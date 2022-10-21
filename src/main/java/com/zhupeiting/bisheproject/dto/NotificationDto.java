package com.zhupeiting.bisheproject.dto;

import com.zhupeiting.bisheproject.model.Users;
import lombok.Data;

@Data
public class NotificationDto {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private String TypeName;
    private Integer noteType;
}
