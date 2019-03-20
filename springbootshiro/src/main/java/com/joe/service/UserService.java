package com.joe.service;

import com.joe.domain.User;
import com.joe.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findByName(String name){
        return userMapper.findByName(name);
    }

    public User findById(Integer id){
        return userMapper.findById(id);
    }
}
