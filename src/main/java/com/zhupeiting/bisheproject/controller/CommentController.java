package com.zhupeiting.bisheproject.controller;

import com.zhupeiting.bisheproject.dto.CommentCreateDto;
import com.zhupeiting.bisheproject.dto.ResultDto;
import com.zhupeiting.bisheproject.exception.CustomizeErrorCode;
import com.zhupeiting.bisheproject.model.Comment;
import com.zhupeiting.bisheproject.model.Users;
import com.zhupeiting.bisheproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

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
        Comment comment = new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setContent(commentDto.getContent());
        comment.setCommentType(commentDto.getCommentType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        //待修改！ users.getId()
        comment.setCommentator(users.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDto.okOf();
    }
}
