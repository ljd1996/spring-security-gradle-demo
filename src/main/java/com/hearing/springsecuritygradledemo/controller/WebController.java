package com.hearing.springsecuritygradledemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Create by hearing on 18-9-2
 */
@Controller
public class WebController {

    @GetMapping("/loginSuccess")
    @ResponseBody
    public String loginSuccess() {
        return "登陆成功";
    }

    @GetMapping("/auth")
    public String auth(@RequestParam("id") String id) {
        System.out.println("id = " + id);
        return "auth";
    }

    @GetMapping("/deny")
    @ResponseBody
    public String deny() {
        return "无权访问!";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "test";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
