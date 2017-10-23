package com.kyh.rest.controller;

import com.kyh.security.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by kongyunhui on 2017/4/25.
 */
@Api(value = "登录管理", description = "登录管理")
@Controller
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private Authentication authentication;

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    /**
     * thymeleaf 默认属性
     * spring.thymeleaf.prefix = classpath:/templates/
     * spring.thymeleaf.suffix = .html
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String helloPage(Model model, Authentication authentication) {
        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        model.addAttribute("currentUser", currentUser);
        LOG.info("currentUser is {}",  currentUser.toString());
        return "index";
    }

    // 异常处理，拒绝访问就重定向到 403 页面
    @RequestMapping("/403")
    public String accessDeniedPage(Model model){
        model.addAttribute("accessDenied", "访问被拒绝，您权限不足！");
        return "403";
    }

    @RequestMapping("/uid")
    @ResponseBody
    public String uid(HttpSession session) {
        return session.getId();
    }

    /**
     * 全局URL拦截测试
     */
    @RequestMapping("/admin/config")
    @ResponseBody
    public void config(){
        System.out.println("--->config for admin user");
    }

    /**
     * 方法级授权测试
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/adminrequire")
    @ResponseBody
    public void adminrequire() {
        System.out.println("--->adminrequire for admin user");
    }

    @ApiOperation(value = "admin用户 | 该id的用户可访问")
    @PreAuthorize("@userServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping("/user/{id}")
    @ResponseBody
    public void getUserPage(@PathVariable Long id){
        System.out.println("--->userInfo page");
    }

    @ApiOperation(value = "当前用户是否通过“记住我”令牌进行身份验证")
    @PreAuthorize("!isRememberMe()")
    @ResponseBody
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public void order() {
        System.out.println("--->order for no rememberMe login user");
    }
}
