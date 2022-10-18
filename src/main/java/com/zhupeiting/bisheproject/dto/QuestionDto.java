package com.zhupeiting.bisheproject.dto;

import com.zhupeiting.bisheproject.model.Users;
import lombok.Data;

@Data
public class QuestionDto {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Users user;
}
