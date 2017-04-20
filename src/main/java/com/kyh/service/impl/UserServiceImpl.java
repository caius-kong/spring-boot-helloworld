package com.kyh.service.impl;

import com.kyh.dao.UserMapper;
import com.kyh.model.User;
import com.kyh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kongyunhui on 2017/4/20.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUser(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int createUser(User user) {
        return userMapper.insert(user);
    }
}
