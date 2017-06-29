package com.kyh.security;

import com.kyh.model.primary.User;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;

/**
 * Created by kongyunhui on 2017/5/5.
 *
 * 扩展userdetails.User，添加可能需要的任何信息
 */
public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;
    private List<String> roles;

    public CurrentUser(User user, List<String> roles) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(roles.toArray(new String[roles.size()])));
        this.user = user;
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    public List<String> getRoles() {
        return this.roles;
    }

}
