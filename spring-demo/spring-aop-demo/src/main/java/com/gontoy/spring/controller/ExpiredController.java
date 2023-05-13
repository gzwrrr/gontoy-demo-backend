package com.gontoy.spring.controller;


import com.gontoy.spring.annotation.AuthChecker;
import com.gontoy.spring.service.SomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expired")
public class ExpiredController {

    @Autowired
    SomeService someService;

    @GetMapping("/status")
    public String getUserInfo() {
        someService.someMethod();
        return "服务正常...";
    }
}
