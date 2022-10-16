package com.zhupeiting.bisheproject.controller;
import com.zhupeiting.bisheproject.dto.AccessTokenDto;
import com.zhupeiting.bisheproject.dto.GithubUser;
import com.zhupeiting.bisheproject.model.Users;
import com.zhupeiting.bisheproject.provider.GithubProvider;
import com.zhupeiting.bisheproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * OAuth授权登录Controller
 * */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           HttpServletResponse response){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setCode(code);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null){
            //登录成功
            Users user = new Users();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatar_url());
            /**
             * 此处每次都创建新用户会导致一个用户有多个token 多条数据库的记录，正确做法应该是先查询数据库，
             * 如果用户已经存在就更新信息就可以不需要写一条新的数据进数据库
             * 当mapper不能完成复杂业务逻辑时 抽象一个service来完成
             */
            //userMapper.insert(user);
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else{
            //登录失败
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        /**
         * 移除后端session和前端cookie
         * 前端移除则无法通过cookie保留登录状态
         * 后端移除session则如果有人盗取cookie也没办法使用cookie恶意访问
         * */
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
