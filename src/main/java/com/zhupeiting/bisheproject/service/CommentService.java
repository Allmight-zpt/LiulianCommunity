package com.zhupeiting.bisheproject.service;

import com.zhupeiting.bisheproject.dto.CommentDto;
import com.zhupeiting.bisheproject.enums.CommentTypeEnum;
import com.zhupeiting.bisheproject.enums.NotificationStatusEnum;
import com.zhupeiting.bisheproject.enums.NotificationTypeEnum;
import com.zhupeiting.bisheproject.exception.CustomizeErrorCode;
import com.zhupeiting.bisheproject.exception.CustomizeException;
import com.zhupeiting.bisheproject.mapper.*;
import com.zhupeiting.bisheproject.model.*;
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
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Transactional
    public void insert(Comment comment,Users commentator) {
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

            /**
             * 查找评论对应的问题
             * */
            System.out.println("???");
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            /***/

            commentMapper.insertSelective(comment);
            //增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);
            //创建通知
            createNotify(comment,dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT,question.getId());
        }else{
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
            //创建通知
            createNotify(comment,question.getCreator(),commentator.getName(),question.getTitle(),NotificationTypeEnum.REPLY_QUESTION,question.getId());
        }
    }

    private void createNotify(Comment comment, Long receiver , String notifierName, String outerTitle, NotificationTypeEnum NotificationType,Long outerid){
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNoteType(NotificationType.getNoteType());
        //这里的outerid 无论是回复问题还是回复评论都是问题的id 因为都需要跳转到问题详情页面查看回复内容
        notification.setOuterid(outerid);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insertSelective(notification);
    }

    public List<CommentDto> listByTargetId(Long id,CommentTypeEnum type) {
        //获取该问题的所有回复
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                        .andParentIdEqualTo(id)
                        .andCommentTypeEqualTo(type.getType());
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

    public void likeOrUnlike(Long id, Long isLike) {
        Comment comment = new Comment();
        comment.setId(id);
        if(isLike == 1){
            //点赞数量++
            comment.setLikeCount(1L);
        }else{
            //点赞数量--
            comment.setLikeCount(-1L);
        }
        commentExtMapper.likeOrUnlike(comment);
    }
}
