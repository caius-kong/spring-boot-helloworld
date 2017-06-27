package com.kyh.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kyh.model.User;
import com.kyh.rest.vo.BaseResult;
import com.kyh.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by kongyunhui on 2017/5/22.
 */
@Api(value = "User", description = "用户管理 RESTFUL API")
@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @ApiOperation(value = "分页查询", notes = "综合查询，参数可选")
    @RequestMapping(value = "/", method = RequestMethod.PATCH)
    public PageInfo<User> list(
            User user,
            @RequestParam(value = "pageNum", required = false, defaultValue="1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue="10") Integer pageSize) {
        PageInfo<User> result = null;
        try {
            /**
             * 紧跟着的 "第一个" 查询方法会被分页 (返回Page<E> extends ArrayList)
             *
             * 如果list()没有消费掉 分页参数 ，本地线程会一直持有该 分页参数，直到下一个查询消费它。(出现你不想分页的查询被分页)
             * 因此，PageHelper和list()必须紧跟才能保证安全性！
             */
            PageHelper.startPage(pageNum, pageSize);
            List<User> list = userService.list(user);
            result = new PageInfo<User>(list);
        } catch (Exception e) {
            log.error("error:{}", e);
        }
        return result;
    }

    @ApiOperation(value = "单体查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String get(@PathVariable Long id) {
        System.out.println("-getUser-->" + id);
        return "userInfo";
    }

    @ApiOperation(value = "增加")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BaseResult insert(User user) {
        BaseResult<User> resp = null;
        try{
            int count = userService.createUser(user);
            if(count > 0)
                resp = new BaseResult<User>(true, user);
        }catch(Exception e){
            resp = new BaseResult(false, e.getMessage());
        }
        return resp;
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id) {
        System.out.println("-deleteUser-->" + id);
        return "userInfo";
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable Long id, User user) {
        System.out.println("-updateUser-->" + user);
        return "userInfo";
    }
}
