package com.kyh.service.impl;

import com.google.common.collect.Lists;
import com.kyh.dao.RoleMapper;
import com.kyh.dao.UserMapper;
import com.kyh.exception.MyException;
import com.kyh.model.Role;
import com.kyh.model.User;
import com.kyh.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kongyunhui on 2017/4/25.
 */
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<String> getRoles(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user==null) new MyException("用戶不存在");
        return roleMapper.getRoles(user.getRoleIds());
    }
}
