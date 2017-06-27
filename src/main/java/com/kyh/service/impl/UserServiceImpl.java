package com.kyh.service.impl;

import com.kyh.constant.StaticParams;
import com.kyh.dao.UserMapper;
import com.kyh.model.User;
import com.kyh.security.CurrentUser;
import com.kyh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by kongyunhui on 2017/4/20.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Cacheable(value = "usercache",keyGenerator = "keyGenerator")
    @Override
    public User findUser(Long id) {
        System.out.println("无缓存，调用这里...");
        return userMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public int createUser(User user) throws Exception{
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userMapper.insertSelective(user);
    }

    @Override
    public boolean canAccessUser(CurrentUser currentUser, Long userId) {
        return currentUser != null
                && (currentUser.getRoles().contains(StaticParams.USERROLE.ROLE_ADMIN) || currentUser.getId().equals(userId));
    }

    @Override
    public List<User> list(User user) throws Exception{
        return userMapper.query(user);
    }
}
