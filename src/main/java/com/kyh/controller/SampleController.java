package com.kyh.controller;

import io.swagger.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Api 表示该类是一个 Swagger 的 Resource, 是对 Controller 进行注解的
 *
 * @RestController: @Controller + @ResponseBody
 */

@Api(value = "API - SampleController", description = "Sample管理")
@RestController
@RequestMapping("/")
public class SampleController {

    @ApiOperation("helloworld接口")
    @RequestMapping(value="", method = RequestMethod.GET)
    public String index() {
        return "Hello World!";
    }

    @ApiOperation(value="打招呼接口", notes="此接口描述的是xxxxxx", httpMethod = "GET", response = String.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name="name", value = "打招呼的目标姓名", required = true, dataType = "string")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value="/sayHello/{name}", method = RequestMethod.GET)
    public String sayHello(@PathVariable("name") String name) {
        return "你好, " + name;
    }
}