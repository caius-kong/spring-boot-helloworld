package com.kyh;

import com.kyh.constant.StaticParams;
import com.kyh.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * 官方文档：https://kielczewski.eu/2014/12/spring-boot-security-application/
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 允许进入页面方法前检验 ==> @PreAuthorize、@PostAuthorize (方法级授权)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;//自定义用户服务

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/hello.html", "/APIs/", "/public/**").permitAll()
                .antMatchers("/APIs/admin/**").hasAuthority("admin")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/APIs/login")
//                .usernameParameter("email")
                .defaultSuccessUrl("/APIs/index.html", true)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/APIs/logout")
                .deleteCookies("remember-me")
//                .and().sessionManagement().maximumSessions(1).expiredUrl("/APIs/expired")
//                .and()
                .and().exceptionHandling().accessDeniedPage("/APIs/403")
                .and()
                .rememberMe();
    }

    // 身份验证配置，用于注入自定义身份验证工具和密码校验规则
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}