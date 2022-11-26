package com.zhupeiting.bisheproject.controller;

import com.zhupeiting.bisheproject.dto.CommentCreateDto;
import com.zhupeiting.bisheproject.dto.CommentDto;
import com.zhupeiting.bisheproject.dto.LikeDto;
import com.zhupeiting.bisheproject.dto.ResultDto;
import com.zhupeiting.bisheproject.enums.CommentTypeEnum;
import com.zhupeiting.bisheproject.exception.CustomizeErrorCode;
import com.zhupeiting.bisheproject.model.Comment;
import com.zhupeiting.bisheproject.model.Users;
import com.zhupeiting.bisheproject.service.CommentService;
import com.zhupeiting.bisheproject.util.SensitiveWordFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private SensitiveWordFilter sensitiveWordFilter;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDto commentDto,
                       HttpServletRequest request){
        Users users = (Users) request.getSession().getAttribute("user");
        if(users == null){
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentDto == null || commentDto.getContent() == null || "".equals(commentDto.getContent())){
            return ResultDto.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        commentDto.setContent(sensitiveWordFilter.replaceSensitiveWord(commentDto.getContent(),SensitiveWordFilter.maxMatchType,sensitiveWordFilter.getReplaceChars("×",1)));
        Comment comment = new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setContent(commentDto.getContent());
        comment.setCommentType(commentDto.getCommentType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        //待修改！ users.getId()
        comment.setCommentator(users.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment,users);
        return ResultDto.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDto<List<CommentDto>> comments(@PathVariable(name = "id")Long id){
        List<CommentDto> commentDtoList = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDto.okOf(commentDtoList);
    }

    @ResponseBody
    @RequestMapping(value = "/comment/like",method = RequestMethod.POST)
    public ResultDto<String> likeOrUnlike(@RequestBody LikeDto likeDto){
        commentService.likeOrUnlike(likeDto.getId(),likeDto.getIsLike());
        return ResultDto.okOf();
    }
}
