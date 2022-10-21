package com.zhupeiting.bisheproject.service;

import com.zhupeiting.bisheproject.dto.PageDto;
import com.zhupeiting.bisheproject.dto.QuestionDto;
import com.zhupeiting.bisheproject.exception.CustomizeErrorCode;
import com.zhupeiting.bisheproject.exception.CustomizeException;
import com.zhupeiting.bisheproject.mapper.QuestionExtMapper;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//questionMapper专门负责question表，当涉及两个表内容的整合时不能再mapper中实现
//要抽象出一个dto传输层的对象负责存储两个表要整合的信息 然后再service调用mapper完成整合
//然后让controller曾调用service层
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UsersMapper usersMapper;

    public PageDto list(Integer page, Integer size) {
        PageDto<QuestionDto> pageDto = new PageDto<>();
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
        /**
         * 分页时列表为空的特殊处理
         * */
        if(page == 0) {
            pageDto.setPageDto(1,1);
            List<QuestionDto>questionDtoList = new ArrayList<>();
            pageDto.setData(questionDtoList);
            return pageDto;
        }
        /***/
        pageDto.setPageDto(totalPage,page);
        Integer offset = size * (page-1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));
        List<QuestionDto>questionDtoList = new ArrayList<>();

        for (Question question : questionList) {
            Users user = usersMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            /**
             * 当description太长时，截取一部分即可
             * */
            String desc = questionDto.getDescription();
            if(desc.length() > 20){
                desc = desc.substring(0,21) + "......";
            }
            questionDto.setDescription(desc);
            /***/
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageDto.setData(questionDtoList);
        return pageDto;
    }

    public PageDto list(Long id, Integer page, Integer size) {
        PageDto<QuestionDto> pageDto = new PageDto<>();
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
        /**
         * 分页时列表为空的特殊处理
         * */
        if(page == 0) {
            pageDto.setPageDto(1,1);
            List<QuestionDto>questionDtoList = new ArrayList<>();
            pageDto.setData(questionDtoList);
            return pageDto;
        }
        /***/
        pageDto.setPageDto(totalPage,page);
        Integer offset = size * (page-1);
        questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(id);
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));
        List<QuestionDto>questionDtoList = new ArrayList<>();
        Users user = usersMapper.selectByPrimaryKey(id);
        for (Question question : questionList) {
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            /**
             * 当description太长时，截取一部分即可
             * */
            String desc = questionDto.getDescription();
            if(desc.length() > 20){
                desc = desc.substring(0,21) + "......";
            }
            questionDto.setDescription(desc);
            /***/
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageDto.setData(questionDtoList);
        return pageDto;
    }

    public QuestionDto getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
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
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
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
            int updated = questionMapper.updateByExampleSelective(updateQuestion,questionExample);
            if(updated != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<QuestionDto> selectRelated(QuestionDto queryDto) {
        if(queryDto.getTag() == null || "".equals(queryDto.getTag())){
            return new ArrayList<>();
        }
        String[] tags = queryDto.getTag().split(",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDto.getId());
        question.setTag(regexpTag);
        List<Question> relatedQuestions = questionExtMapper.selectRelated(question);
        List<QuestionDto> relatedQuestionsDto = relatedQuestions.stream().map(q->{
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(q,questionDto);
            return questionDto;
        }).collect(Collectors.toList());
        return relatedQuestionsDto;
    }
}
