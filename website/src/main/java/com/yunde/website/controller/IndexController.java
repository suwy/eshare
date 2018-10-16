package com.yunde.website.controller;

import com.yunde.frame.log.YundeLog;
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

//    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @GetMapping
    public String welcome() {
//        LOG.info("come in please {}", "everybody");
        YundeLog.info("Someone visit，the page {} {} ", "welcome", "show time!");
        System.out.println();
        return "hello world, welcome! 欢迎光临！";
    }
}
