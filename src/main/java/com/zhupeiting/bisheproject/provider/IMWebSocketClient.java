package com.zhupeiting.bisheproject.provider;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class IMWebSocketClient extends WebSocketClient {
    public IMWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("连接成功");
    }

    @Override
    public void onMessage(String s) {
        log.info("收到消息: " + s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("连接关闭");
    }

    @Override
    public void onError(Exception e) {
        log.info("发生错误");
    }
}
