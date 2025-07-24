package com.suifeng.interview.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试controller
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class DemoController {
    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
