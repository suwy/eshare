package com.yunde.website.controller;

import com.yunde.frame.tools.ResultMsg;
import com.yunde.website.entity.User;
import com.yunde.website.service.IRedisService;
import com.yunde.website.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private IRedisService redisService;

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
        try {
            redisService.set(name, "654321");
            redisService.expire(name, 30);
            System.out.println("redis now key="+name+", value="+redisService.get(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultMsg.success(user);
    }

    @RequestMapping(value = "showRedisValue", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查看验证码")
    public ResultMsg showRedisValue(String name) {
        try {
            String value = redisService.get(name);
            long time = redisService.getExpire(name);
            return ResultMsg.success(value+ " ，time="+time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
