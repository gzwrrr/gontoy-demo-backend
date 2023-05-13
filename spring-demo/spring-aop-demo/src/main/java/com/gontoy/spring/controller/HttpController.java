package com.gontoy.spring.controller;

import com.gontoy.spring.annotation.AuthChecker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop")
public class HttpController {

    @GetMapping("/userInfo")
    @AuthChecker
    public String getUserInfo() {
        return "获取用户信息...";
    }


    @GetMapping("/status")
    @AuthChecker
    public String getStatus() {
        return "服务正常运行...";
    }

}
