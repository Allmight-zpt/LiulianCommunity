package com.zhupeiting.bisheproject.model;

import lombok.Data;

@Data
public class Users {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
