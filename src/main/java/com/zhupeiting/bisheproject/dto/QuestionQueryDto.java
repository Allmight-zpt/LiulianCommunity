package com.zhupeiting.bisheproject.dto;

import lombok.Data;

@Data
public class QuestionQueryDto {
    private String search;
    private String tag;
    private Integer page;
    private Integer size;
}
