package com.yunde.website.controller;

import com.yunde.frame.tools.ResultMsg;
import com.yunde.website.entity.User;
import com.yunde.website.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author laisy
 * @date 2019/9/7
 * 登录页
 */
@RestController
@RequestMapping("/account")
//@ApiModel(value="SysLog对象", description="系统日志")
@Api(value = "登录模块", tags = "账号模块")
public class AccountController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "password", value = "密码", required = true, dataType = "String")
    })
    public ResultMsg<User> login(String name, String password) {
//    public ResultMsg<User> login(@ApiParam(name="name",value="用户名",required=true) String name,
//                           @ApiParam(name="password",value="密码",required=true) String password) {
        User user = userService.findUserByNameAndPassword(name, password);
        return ResultMsg.success(user);
    }
}
