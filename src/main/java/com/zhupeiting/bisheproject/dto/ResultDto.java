package com.zhupeiting.bisheproject.dto;

import com.zhupeiting.bisheproject.exception.CustomizeErrorCode;
import com.zhupeiting.bisheproject.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDto {
    private String message;
    private Integer code;
    public static ResultDto errorOf(Integer code,String message){
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }
    public static ResultDto errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    public static ResultDto errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDto okOf() {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        return resultDto;
    }

}
