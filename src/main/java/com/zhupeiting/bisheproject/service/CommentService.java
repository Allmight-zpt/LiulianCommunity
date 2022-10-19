package com.zhupeiting.bisheproject.service;

import com.zhupeiting.bisheproject.dto.CommentCreateDto;
import com.zhupeiting.bisheproject.dto.CommentDto;
import com.zhupeiting.bisheproject.enums.CommentTypeEnum;
import com.zhupeiting.bisheproject.exception.CustomizeErrorCode;
import com.zhupeiting.bisheproject.exception.CustomizeException;
import com.zhupeiting.bisheproject.mapper.CommentMapper;
import com.zhupeiting.bisheproject.mapper.QuestionExtMapper;
import com.zhupeiting.bisheproject.mapper.QuestionMapper;
import com.zhupeiting.bisheproject.mapper.UsersMapper;
import com.zhupeiting.bisheproject.model.*;
import org.h2.engine.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UsersMapper usersMapper;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getCommentType() == null || !CommentTypeEnum.isExist(comment.getCommentType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_NOT_FOUND);
        }
        if(comment.getCommentType() == CommentTypeEnum.COMMENT.getType()){
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
        }else{
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }
    }

    public List<CommentDto> listByQuestionId(Long id) {
        //获取该问题的所有回复
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                        .andParentIdEqualTo(id)
                        .andCommentTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size() == 0){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        //获取评论人并转换为map
        UsersExample usersExample = new UsersExample();
        usersExample.createCriteria()
                .andIdIn(userIds);
        List<Users> users = usersMapper.selectByExample(usersExample);
        Map<Long,Users> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));
        //转换comment 为commentDto
        List<CommentDto>commentDtos = comments.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setUsers(userMap.get(comment.getCommentator()));
            return commentDto;
        }).collect(Collectors.toList());
        return commentDtos;
    }
}
