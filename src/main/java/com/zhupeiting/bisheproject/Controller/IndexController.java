package com.zhupeiting.bisheproject.Controller;

import com.zhupeiting.bisheproject.dto.QuestionDto;
import com.zhupeiting.bisheproject.mapper.UserMapper;
import com.zhupeiting.bisheproject.model.Question;
import com.zhupeiting.bisheproject.model.Users;
import com.zhupeiting.bisheproject.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Value("${github.authorize.uri.with.params}")
    private String authorizeUriWithParams;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request ,Model model){
        String uri = authorizeUriWithParams;
        model.addAttribute("uri",uri);
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    Users user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        List<QuestionDto> questionDtoList = questionService.list();
        model.addAttribute("questionDtoList",questionDtoList);
        return "index";
    }
}
