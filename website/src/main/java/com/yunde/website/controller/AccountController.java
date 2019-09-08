package com.yunde.website.controller;

import com.yunde.frame.tools.ResultMsg;
import com.yunde.website.entity.User;
import com.yunde.website.service.UserService;
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
public class AccountController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResultMsg login(String name, String password) {
        User user = userService.findUserByNameAndPassword(name, password);
        return ResultMsg.success(user);
    }
}
