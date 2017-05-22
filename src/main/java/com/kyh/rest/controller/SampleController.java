package com.kyh.rest.controller;

import com.kyh.exception.MyException;
import com.kyh.model.User;
import com.kyh.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @Api 表示该类是一个 Swagger 的 Resource, 是对 Controller 进行注解的
 *
 * @RestController: @Controller + @ResponseBody
 */

@Api(value = "SampleController", description = "Sample管理")
@RestController
@RequestMapping("/")
public class SampleController {
    @Autowired
    private UserService userService;

    @ApiOperation("helloworld接口")
    @RequestMapping(value="", method = RequestMethod.GET)
    public String index() {
        return "Hello World!" + SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @ApiOperation(value="打招呼接口", notes="此接口描述的是和某人打招呼的操作")
    @ApiImplicitParams({
        @ApiImplicitParam(name="name", value = "目标姓名", required = true, dataType = "string", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value="/sayHello", method = RequestMethod.POST, headers = "version=1.0")
    public String sayHello(@RequestParam String name, @RequestHeader("version") String version) {
        return "你好, " + name;
    }

    @RequestMapping("/errorHtml")
    public User errorHtml() throws Exception{
        throw new Exception("发生错误");
    }

    @RequestMapping("/errorJson")
    public User errorJson() throws Exception{
        throw new MyException("发生错误");
    }
}