package com.zhupeiting.bisheproject.mapper;

import com.zhupeiting.bisheproject.dto.QuestionQueryDto;
import com.zhupeiting.bisheproject.model.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDto questionQueryDto);

    List<Question> selectBySearch(QuestionQueryDto questionQueryDto);
}