package com.kyh.service;

import com.kyh.model.User;

/**
 * Created by kongyunhui on 2017/4/20.
 */
public interface UserService {
    User findUser(Long id);

    int createUser(User user);

}
