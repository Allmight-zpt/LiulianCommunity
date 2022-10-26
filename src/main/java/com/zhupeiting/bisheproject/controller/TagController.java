package com.zhupeiting.bisheproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TagController {
    @GetMapping("/hotTag")
    public String getPage(){
        return "hotTag";
    }
}
