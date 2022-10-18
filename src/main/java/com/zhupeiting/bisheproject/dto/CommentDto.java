package com.zhupeiting.bisheproject.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long parentId;
    private String content;
    private Integer commentType;
}
