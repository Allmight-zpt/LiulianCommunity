package com.zhupeiting.bisheproject.Controller;

import com.zhupeiting.bisheproject.mapper.QuestionMapper;
import com.zhupeiting.bisheproject.model.Question;
import com.zhupeiting.bisheproject.model.Users;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Value("${github.authorize.uri.with.params}")
    private String authorizeUriWithParams;
    @Autowired
    QuestionMapper questionMapper;
    // get 渲染页面
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    // post 发送请求（publish中的发布功能调用该接口是post方式）
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false)String title,
            @RequestParam(value = "description",required = false)String description,
            @RequestParam(value = "tag",required = false)String tag,
            HttpServletRequest request,
            Model model
            ) {
        String uri = authorizeUriWithParams;
        model.addAttribute("uri",uri);
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if(title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description == null || description == ""){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }
        if(tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        Users user = null;
        user = (Users) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}
