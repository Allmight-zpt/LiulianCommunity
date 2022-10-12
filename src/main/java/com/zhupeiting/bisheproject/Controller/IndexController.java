package com.zhupeiting.bisheproject.Controller;

import com.zhupeiting.bisheproject.mapper.UserMapper;
import com.zhupeiting.bisheproject.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Value("${github.authorize.uri.with.params}")
    private String authorizeUriWithParams;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request ,Model model){
        String uri = authorizeUriWithParams;
        model.addAttribute("uri",uri);
        Cookie[] cookies = request.getCookies();
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

        return "index";
    }
}
