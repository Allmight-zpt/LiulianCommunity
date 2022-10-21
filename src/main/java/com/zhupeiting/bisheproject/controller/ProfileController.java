package com.zhupeiting.bisheproject.controller;

import com.zhupeiting.bisheproject.dto.PageDto;
import com.zhupeiting.bisheproject.model.Users;
import com.zhupeiting.bisheproject.service.NotificationService;
import com.zhupeiting.bisheproject.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "5")Integer size){
        Users users = (Users)request.getSession().getAttribute("user");
        if(users == null){
            return "redirect:/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PageDto pageDto = questionService.list(users.getId(),page,size);
            model.addAttribute("pageDto",pageDto);
        } else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");

            Long unreadCount = notificationService.unreadCount(users.getId());
            model.addAttribute("unreadCount",unreadCount);
            System.out.println("???"+unreadCount);
            PageDto pageDto = notificationService.list(users.getId(),page,size);
            model.addAttribute("pageDto",pageDto);
        }

        return "profile";
    }
}
