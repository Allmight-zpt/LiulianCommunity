package com.zhupeiting.bisheproject.mapper;

import com.zhupeiting.bisheproject.model.Question;
import com.zhupeiting.bisheproject.model.QuestionExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
}