package com.zhupeiting.bisheproject.mapper;

import com.zhupeiting.bisheproject.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);

    void likeOrUnlike(Comment comment);
}
