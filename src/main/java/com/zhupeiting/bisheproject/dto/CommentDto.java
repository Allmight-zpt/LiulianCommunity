package com.zhupeiting.bisheproject.dto;

import com.zhupeiting.bisheproject.model.Users;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private Long parentId;
    private Integer commentType;
    private Integer commentCount;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private Users users;
}
