package com.zhupeiting.bisheproject.dto;

import lombok.Data;

@Data
public class CommentCreateDto {
    private Long parentId;
    private String content;
    private Integer commentType;
}
