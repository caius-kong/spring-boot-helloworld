package com.kyh.service;

import com.kyh.model.User;
import com.kyh.security.CurrentUser;

/**
 * Created by kongyunhui on 2017/4/20.
 */
public interface UserService {
    User findByUsername(String username);

    User findUser(Long id);

    int createUser(User user);

    boolean canAccessUser(CurrentUser currentUser, Long userId);

}
