package com.zhupeiting.bisheproject.controller;

import com.zhupeiting.bisheproject.dto.FileDto;
import com.zhupeiting.bisheproject.dto.FileUploadResultDto;
import com.zhupeiting.bisheproject.provider.FSProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class FileController {
    @Autowired
    private FSProvider fsProvider;
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDto upload(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        //实现图片上传
        FileUploadResultDto fileUploadResultDto = fsProvider.upload(file);
        System.out.println(fileUploadResultDto);
        FileDto fileDto = new FileDto();
        fileDto.setSuccess(fileUploadResultDto.getCode() == 200?1:0);
        fileDto.setUrl(fileUploadResultDto.getUrl());
        fileDto.setMessage(fileUploadResultDto.getMsg());
        return fileDto;
    }
}
