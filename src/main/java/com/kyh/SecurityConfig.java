package com.kyh;

import com.kyh.constant.StaticParams;
import com.kyh.security.MyLogoutHandler;
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
import org.springframework.security.web.csrf.CsrfLogoutHandler;


/**
 * 官方文档：https://kielczewski.eu/2014/12/spring-boot-security-application/
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 允许进入页面方法前检验 ==> @PreAuthorize、@PostAuthorize (方法级授权)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;//自定义用户服务

    @Autowired
    private MyLogoutHandler logoutHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/APIs/", "/APIs/hello.html", "/APIs/css/**", "/APIs/js/**", "/APIs/images/**").permitAll() // 指定放行的URL模式(主要就是调整：静态资源不做安全控制；/APIs/是查看当前用户的接口，测试专用，放行)
                .antMatchers("/APIs/admin/**").hasAuthority("admin")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/APIs/login")
//                .usernameParameter("email")
                .defaultSuccessUrl("/APIs/index", true)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/APIs/logout")
                .addLogoutHandler(logoutHandler) // 注销时进行必要的清理。如果是注销日志的话，使用.logoutSuccessHandler()
                .deleteCookies("remember-me","JSESSIONID","SESSION")
                .permitAll()
//                .and().sessionManagement().maximumSessions(1).expiredUrl("/APIs/expired")
//                .and()
                .and().exceptionHandling().accessDeniedPage("/APIs/403")
                .and()
                .rememberMe();
//                .and()
//                .csrf().disable(); // 禁用"同步器标记模式"/CSRF保护
    }

    // 身份验证配置，用于注入自定义身份验证工具和密码校验规则
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}