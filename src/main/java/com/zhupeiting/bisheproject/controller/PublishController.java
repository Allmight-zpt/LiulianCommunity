package com.zhupeiting.bisheproject.controller;

import com.zhupeiting.bisheproject.cache.TagCache;
import com.zhupeiting.bisheproject.dto.QuestionDto;
import com.zhupeiting.bisheproject.dto.TagDto;
import com.zhupeiting.bisheproject.model.Question;
import com.zhupeiting.bisheproject.model.Users;
import com.zhupeiting.bisheproject.service.QuestionService;
import com.zhupeiting.bisheproject.util.SensitiveWordFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PublishController {
    @Value("${github.authorize.uri.with.params}")
    private String authorizeUriWithParams;
    @Autowired
    QuestionService questionService;
    @Autowired
    SensitiveWordFilter sensitiveWordFilter;
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model){
        QuestionDto question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
    // get 渲染页面
    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
    // post 发送请求（publish中的发布功能调用该接口是post方式）
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false)String title,
            @RequestParam(value = "description",required = false)String description,
            @RequestParam(value = "tag",required = false)String tag,
            @RequestParam(value = "id" ,required = false)Long id,
            HttpServletRequest request,
            Model model
            ) {
        String uri = authorizeUriWithParams;
        model.addAttribute("uri",uri);
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());
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
        Users user = (Users) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        String invalid = TagCache.filterInvalid(tag);
        if(invalid != null && !("".equals(invalid))){
            model.addAttribute("error","存在非法标签：" + invalid);
            return "publish";
        }
        description = sensitiveWordFilter.replaceSensitiveWord(description,SensitiveWordFilter.maxMatchType, sensitiveWordFilter.getReplaceChars("×",1));
        title = sensitiveWordFilter.replaceSensitiveWord(title,SensitiveWordFilter.maxMatchType, sensitiveWordFilter.getReplaceChars("×",1));
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
