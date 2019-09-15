package com.yunde.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author: suwy
 * @date: 2019-09-15
 * @decription:
 */
@Controller
@RequestMapping("/docs")
@ApiIgnore
public class ApiDocController extends BaseController {

    /**
     * swaggerUI
     */
    @GetMapping("")
    public String swaggerUI(){
        return "redirect:/swagger-ui.html";
    }

}
