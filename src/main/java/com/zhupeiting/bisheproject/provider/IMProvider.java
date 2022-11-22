package com.zhupeiting.bisheproject.provider;

import com.alibaba.fastjson2.JSON;
import com.zhupeiting.bisheproject.dto.ChatDataDto;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class IMProvider{
    @Value("${getChatData.url}")
    private String getChatDataUrl;

    /**
     * 获取历史聊天记录
     * */
    public ChatDataDto getChatData(String accountId){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(getChatDataUrl + accountId).build();
        //生成一个准备好请求的call对象
        Call call = okHttpClient.newCall(request);
        ChatDataDto chatDataDto = new ChatDataDto();
        try {
            //execute会阻塞线程
            Response response = call.execute();
            chatDataDto = JSON.parseObject(response.body().string(), ChatDataDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chatDataDto;
    }
}