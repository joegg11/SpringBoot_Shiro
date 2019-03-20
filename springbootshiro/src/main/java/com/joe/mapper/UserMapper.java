package com.joe.mapper;

import com.joe.domain.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from user where name=#{value}")
    public User findByName(String name);

    @Select("select * from user where id=#{value}")
    public User findById(Integer id);
}
