package com.wang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {

    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "views/login";
    }

    //实现路径的复用 (利用前端传来的数据实现不同的跳转, 返回值拼接字符串)
    @RequestMapping("/level1/{id}")
    public String toLevel1(@PathVariable("id") int id) {
        return "views/level1/" + id;
    }

    //实现路径的复用 (利用前端传来的数据实现不同的跳转, 返回值拼接字符串)
    @RequestMapping("/level2/{id}")
    public String toLevel2(@PathVariable("id") int id) {
        return "views/level2/" + id;
    }

    //实现路径的复用 (利用前端传来的数据实现不同的跳转, 返回值拼接字符串)
    @RequestMapping("/level3/{id}")
    public String toLevel3(@PathVariable("id") int id) {
        return "views/level3/" + id;
    }
}
