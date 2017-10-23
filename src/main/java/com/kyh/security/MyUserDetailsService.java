package com.kyh.security;

import com.kyh.model.primary.User;
import com.kyh.service.RoleService;
import com.kyh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 自定义身份验证工具
 *
 * 原理：UsernamePasswordAuthenticationFilter --> AuthenticationManager --> ProviderManager
 *      --> AuthenticationProvider(系统默认的) --> UserDetailsService.loadUserByUsername（自定义）
 *
 * 因此，如果不用UserDetailsService，也可以直接自定义AuthenticationProvider(更加灵活)
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("用户名为空");
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with username=%s was not found", username));
        }
        if (user.getLocked() == 1) {
            throw new LockedException(String.format("User with username=%s was locked", username));
        }

//        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
//        roleService.getRoles(user.getId()).forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getName())));
//
//        return new org.springframework.security.core.userdetails.User(
//                username, user.getPassword(),
//                true,//是否可用
//                true,//是否过期
//                true,//证书不过期为true
//                true,//账户未锁定为true
//                authorities);

        return new CurrentUser(user, roleService.getRoles(user.getId()));
    }
}