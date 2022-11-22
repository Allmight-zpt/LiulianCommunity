package com.zhupeiting.bisheproject.provider;

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
import java.util.List;

@Component
@Slf4j
public class IMProvider{
    @Value("${webSocket.server.url}")
    private String webSocketServerUrl;
    @Value("${getChatData.url}")
    private String getChatDataUrl;

    public void createWebSocketConnect(String userID,HttpServletRequest request){
        IMWebSocketClient imClient = null;
        try {
            imClient = new IMWebSocketClient(new URI(webSocketServerUrl + userID));
            imClient.connect();
            // 判断是否连接成功，未成功后面发送消息时会报错
            while (!imClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                log.info("与IM系统建立连接中···请稍后");
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("IMClient",imClient);
    }

    public void sendMessageToClient(String message,HttpServletRequest request){
        IMWebSocketClient imClient = (IMWebSocketClient) request.getSession().getAttribute("IMClient");
        if(imClient != null){
            log.info("发送消息: " + message);
            imClient.send(message);
        }
    }

    public List<String> getChatData(String accountId){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(getChatDataUrl + accountId).build();
        //生成一个准备好请求的call对象
        Call call = okHttpClient.newCall(request);
        try {
            //execute会阻塞线程
            Response response = call.execute();
            return Collections.singletonList(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}