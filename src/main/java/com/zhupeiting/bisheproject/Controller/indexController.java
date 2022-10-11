package com.zhupeiting.bisheproject.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class indexController {
    @Value("${github.authorize.uri}")
    private String authorizeUri;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @GetMapping("/")
    public String index(Model model){
        String Uri = authorizeUri + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=code";
        model.addAttribute("Uri",Uri);
        return "index";
    }
}
