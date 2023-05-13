package com.gontoy.spring.controller;

import com.gontoy.spring.service.NeedLogService;
import com.gontoy.spring.service.NormalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AOP 日志 API
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    NeedLogService needLogService;

    @Autowired
    NormalService normalService;

    @GetMapping("/needLog/good")
    public String needLogGood() {
        needLogService.logMethod("args1");
        return "调用 needLog，正常...";
    }

    @GetMapping("/needLog/bad")
    public String needLogBad() throws Exception {
        needLogService.exceptionMethod();
        return "调用 needLog，抛出异常...";
    }

    @GetMapping("/normal")
    public String normal() {
        normalService.someMethod();
        return "调用 normal...";
    }


}
