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
            PageDto pageDto = notificationService.list(users.getId(),page,size);
            model.addAttribute("pageDto",pageDto);
        } else if("mails".equals(action)){
            model.addAttribute("section","mails");
            model.addAttribute("sectionName","我的邮箱");
            PageDto pageDto = notificationService.list(users.getId(),page,size);
            model.addAttribute("pageDto",pageDto);
        } else if("fans".equals(action)){
            model.addAttribute("section","fans");
            model.addAttribute("sectionName","我的粉丝");
            PageDto pageDto = notificationService.list(users.getId(),page,size);
            model.addAttribute("pageDto",pageDto);
        } else if("idols".equals(action)){
            model.addAttribute("section","idols");
            model.addAttribute("sectionName","我关注的人");
            PageDto pageDto = notificationService.list(users.getId(),page,size);
            model.addAttribute("pageDto",pageDto);
        } else if("collectQuestions".equals(action)){
            model.addAttribute("section","collectQuestions");
            model.addAttribute("sectionName","我收藏的问题");
            PageDto pageDto = notificationService.list(users.getId(),page,size);
            model.addAttribute("pageDto",pageDto);
        } else if("followTags".equals(action)){
            model.addAttribute("section","followTags");
            model.addAttribute("sectionName","我关注的话题");
            PageDto pageDto = notificationService.list(users.getId(),page,size);
            model.addAttribute("pageDto",pageDto);
        } else if("followActivities".equals(action)){
            model.addAttribute("section","followActivities");
            model.addAttribute("sectionName","我关注的活动");
            PageDto pageDto = notificationService.list(users.getId(),page,size);
            model.addAttribute("pageDto",pageDto);
        }
        return "profile";
    }

    @GetMapping("/profile/myHome")
    public String myHome(){
        return "myHome";
    }

    @GetMapping("/profile/updateInfo")
    public String updateInfo(){
        return "updateInfo";
    }
}
