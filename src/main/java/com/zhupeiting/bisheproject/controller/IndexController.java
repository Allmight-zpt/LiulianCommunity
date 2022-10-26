package com.zhupeiting.bisheproject.controller;

import com.zhupeiting.bisheproject.cache.HotTagCache;
import com.zhupeiting.bisheproject.dto.HotTagDto;
import com.zhupeiting.bisheproject.dto.PageDto;
import com.zhupeiting.bisheproject.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size,
                        @RequestParam(name = "search",required = false)String search,
                        @RequestParam(name = "tag",required = false)String tag){
        PageDto pageDto = questionService.list(search,tag,page,size);
        List<HotTagDto> tags = hotTagCache.getHots();
        model.addAttribute("pageDto",pageDto);
        model.addAttribute("search",search);
        model.addAttribute("tag",tag);
        model.addAttribute("tags",tags);
        return "index";
    }

    @GetMapping("/support")
    public String support(){
        return "support";
    }
}
