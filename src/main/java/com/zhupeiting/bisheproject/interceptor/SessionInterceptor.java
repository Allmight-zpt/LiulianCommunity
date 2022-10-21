package com.zhupeiting.bisheproject.interceptor;

import com.zhupeiting.bisheproject.mapper.UsersMapper;
import com.zhupeiting.bisheproject.model.Users;
import com.zhupeiting.bisheproject.model.UsersExample;
import com.zhupeiting.bisheproject.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Value("${github.authorize.uri.with.params}")
    private String authorizeUriWithParams;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    UsersExample usersExample = new UsersExample();
                    usersExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<Users> user = usersMapper.selectByExample(usersExample);
                    if(user.size() != 0){
                        request.getSession().setAttribute("user",user.get(0));
                        Long unreadCount = notificationService.unreadCount(user.get(0).getId());
                        request.getSession().setAttribute("unreadCount",unreadCount);
                    }
                    break;
                }
            }
        }
        if(request.getSession().getAttribute("authorizeUriWithParams") == null){
            request.getSession().setAttribute("authorizeUriWithParams",authorizeUriWithParams);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
