package com.zhupeiting.bisheproject.dto;

import lombok.Data;

import java.util.List;
@Data
public class ChatDataDto {
    String message;
    String code;
    List<String> data;
}
