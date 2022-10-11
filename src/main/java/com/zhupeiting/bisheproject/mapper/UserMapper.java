package com.zhupeiting.bisheproject.mapper;

import com.zhupeiting.bisheproject.model.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into users (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(Users user);

    @Select("select * from users where token = #{token}")
    Users findByToken(@Param("token") String token);
}
