package com.zhupeiting.bisheproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatOnline {
    @GetMapping("chatRoom")
    public String chat(){
        return "chatRoom";
    }
}
