package com.zhupeiting.bisheproject.service;

import com.zhupeiting.bisheproject.dto.QuestionDto;
import com.zhupeiting.bisheproject.mapper.QuestionMapper;
import com.zhupeiting.bisheproject.mapper.UserMapper;
import com.zhupeiting.bisheproject.model.Question;
import com.zhupeiting.bisheproject.model.Users;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//questionMapper专门负责question表，当涉及两个表内容的整合时不能再mapper中实现
//要抽象出一个dto传输层的对象负责存储两个表要整合的信息 然后再service调用mapper完成整合
//然后让controller曾调用service层
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public List<QuestionDto> list() {
        List<Question>questionList = questionMapper.list();
        List<QuestionDto>questionDtoList = new ArrayList<>();
        for (Question question : questionList) {
            Users user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        return questionDtoList;
    }
}
