package com.zhupeiting.bisheproject.controller;

import com.zhupeiting.bisheproject.provider.IMProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IMController {

    @Autowired
    IMProvider imProvider;

    @ResponseBody
    @RequestMapping("/chatData/{accountId}")
    public List<String>getChatData(@PathVariable("accountId") String accountId){
        return imProvider.getChatData(accountId);
    }
}
