package com.yunde.wechat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by laisy on 2018/8/7.
 * 主页
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String welcome() {
        return "hello world, welcome! 欢迎光临！";
    }
}
