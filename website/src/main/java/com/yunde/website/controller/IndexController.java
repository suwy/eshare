package com.yunde.website.controller;

import com.yunde.frame.log.YundeLog;
import com.yunde.frame.tools.ResultMsg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 *
 * @author laisy
 * @date 2018/8/7
 * 主页
 */
@RestController
@RequestMapping("/")
public class IndexController extends BaseController {

    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public String welcome() {
        //todo 1、{}的匹配还需要优化 2、详细类名也需要显示
//        LOG.info("come in please {}", "everybody");
        YundeLog.info("Someone visit，the page");
        return "hello world, welcome! 欢迎光临！";
    }

    //若value没写 就用controller顶部的
    @RequestMapping(value="base", method = RequestMethod.GET)
    public ResultMsg base() {
        List list = new ArrayList() {{
            for (int i = 0; i < 3; i++) {
                add(new HashMap<Object, Object>(){{
                    put("id", Collections.nCopies(2, "a"));
                    put("name", Collections.nCopies(5,  "b"));
                }});
            }
        }};
        Map<String, String> keys = new HashMap(2) {{
           put("id","菜单号");
           put("name","菜单名称");
        }};
        return formateData(list, keys);
    }

    public ResultMsg login() {
        return ResultMsg.success("login");
    }
}
