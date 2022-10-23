package com.zhupeiting.bisheproject.dto;

import lombok.Data;

@Data
public class FileUploadResultDto {
    private String msg;
    private int code;
    private String url;
    private String smUrl;
}
