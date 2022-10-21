package com.zhupeiting.bisheproject.controller;

import com.zhupeiting.bisheproject.dto.NotificationDto;
import com.zhupeiting.bisheproject.enums.NotificationTypeEnum;
import com.zhupeiting.bisheproject.model.Users;
import com.zhupeiting.bisheproject.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String notification(HttpServletRequest request,
                               @PathVariable(name = "id")Long id){
        Users users = (Users) request.getSession().getAttribute("user");
        if(users == null){
            return "redirect:/";
        }
        NotificationDto notificationDto = notificationService.read(id,users);
        if(NotificationTypeEnum.REPLY_COMMENT.getNoteType() == notificationDto.getNoteType()
        || NotificationTypeEnum.REPLY_QUESTION.getNoteType() == notificationDto.getNoteType()){
            return "redirect:/question/" + notificationDto.getOuterid();
        }else{
            //待完善！！
            return "redirect:/";
        }
    }
}
