package com.yunde.website.controller;

import com.yunde.frame.log.YundeLog;
import com.yunde.frame.tools.ResultMsg;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by laisy on 2018/8/7.
 * 主页
 */
@RestController
@RequestMapping("/")
public class IndexController extends BaseController {

//    private static final Logger LOG = LoggerFactory.getLogger(getClass());

    @GetMapping
    public String welcome() {
        //todo 1、{}的匹配还需要优化 2、详细类名也需要显示
//        LOG.info("come in please {}", "everybody");
        YundeLog.info("Someone visit，the page");
        return "hello world, welcome! 欢迎光临！";
    }

//    public ResultMsg login(String account, String password) {
//        return formateData();
//    }
}
