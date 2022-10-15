package com.zhupeiting.bisheproject.mapper;

import com.zhupeiting.bisheproject.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(Integer offset, Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator=#{id}")
    Integer countById(Integer id);

    @Select("select * from question where creator=#{id} limit #{offset},#{size}")
    List<Question> listById(Integer id, Integer offset, Integer size);

    @Select("select * from question where id=#{id}")
    Question getById(Integer id);

    @Select("update question set title=#{title},description=#{description},gmt_modified = #{gmtModified},tag = #{tag} where id =#{id}")
    void update(Question question);

}
