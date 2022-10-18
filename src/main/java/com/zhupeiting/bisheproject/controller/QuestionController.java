package com.zhupeiting.bisheproject.controller;

import com.zhupeiting.bisheproject.dto.QuestionDto;
import com.zhupeiting.bisheproject.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        QuestionDto questionDto = questionService.getById(id);
        questionService.incView(id);
        model.addAttribute("questionDto",questionDto);
        return "question";
    }
}
