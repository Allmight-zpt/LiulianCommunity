package com.zhupeiting.bisheproject.service;

import com.zhupeiting.bisheproject.dto.PageDto;
import com.zhupeiting.bisheproject.dto.QuestionDto;
import com.zhupeiting.bisheproject.mapper.QuestionMapper;
import com.zhupeiting.bisheproject.mapper.UsersMapper;
import com.zhupeiting.bisheproject.model.Question;
import com.zhupeiting.bisheproject.model.QuestionExample;
import com.zhupeiting.bisheproject.model.Users;
import org.apache.ibatis.session.RowBounds;
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
    private UsersMapper usersMapper;

    public PageDto list(Integer page, Integer size) {
        PageDto pageDto = new PageDto();
        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
        //计算总页数
        Integer totalPage;
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }
        //检查page
        if(page < 1){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }
        pageDto.setPageDto(totalPage,page);
        Integer offset = size * (page-1);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        List<QuestionDto>questionDtoList = new ArrayList<>();

        for (Question question : questionList) {
            Users user = usersMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageDto.setQuestions(questionDtoList);

        return pageDto;
    }

    public PageDto list(Integer id, Integer page, Integer size) {
        PageDto pageDto = new PageDto();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(id);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
        //计算总页数
        Integer totalPage;
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }
        //检查page
        if(page < 1){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }
        pageDto.setPageDto(totalPage,page);
        Integer offset = size * (page-1);
        questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(id);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));
        List<QuestionDto>questionDtoList = new ArrayList<>();
        Users user = usersMapper.selectByPrimaryKey(id);
        for (Question question : questionList) {
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageDto.setQuestions(questionDtoList);
        return pageDto;
    }

    public QuestionDto getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        Users users = usersMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(users);
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            //创建
            //???存在问题 id为null时要用selective让其自增
            questionMapper.insertSelective(question);
        }else{
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                            .andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion,questionExample);
        }
    }
}
