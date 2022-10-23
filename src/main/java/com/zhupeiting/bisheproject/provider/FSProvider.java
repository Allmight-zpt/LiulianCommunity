package com.zhupeiting.bisheproject.provider;

import com.alibaba.fastjson2.JSON;
import com.zhupeiting.bisheproject.dto.FileUploadResultDto;
import okhttp3.*;
import okio.ByteString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FSProvider {
    @Value("${file.upload.url}")
    private String fileUploadUrl;
    @Value("${file.preview.url.prefix}")
    private String filePreviewUrlPrefix;


    public FileUploadResultDto upload(MultipartFile file) throws IOException {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        final File excelFile = File.createTempFile(UUID.randomUUID().toString(), prefix);
        // MultipartFile to File
        file.transferTo(excelFile);
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody multipartBody = new MultipartBody.Builder()
                //此处的file据名必须与请求接口接收时指定的名称一致！！！
                .addFormDataPart("file",file.getOriginalFilename(), RequestBody.create(MediaType.parse("application/x-jpg"), excelFile))
                .build();
        Request request = new Request.Builder().url(fileUploadUrl).post(multipartBody).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            FileUploadResultDto fileUploadResultDto = JSON.parseObject(response.body().string(), FileUploadResultDto.class);
            fileUploadResultDto.setUrl(filePreviewUrlPrefix+'/'+fileUploadResultDto.getUrl());
            return fileUploadResultDto;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if(excelFile.exists()){
                excelFile.delete();
            }
        }
    }
}
