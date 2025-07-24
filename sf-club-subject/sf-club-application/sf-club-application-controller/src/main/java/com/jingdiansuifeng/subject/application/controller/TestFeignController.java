package com.jingdiansuifeng.subject.application.controller;

import com.jingdiansuifeng.subject.domain.entity.UserInfo;
import com.jingdiansuifeng.subject.infra.rpc.UserRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/subject/category")
@Slf4j
public class TestFeignController {

    @Resource
    private UserRpc userRpc;


    @GetMapping("/testFeign")
    public void testFeign() {
        UserInfo userInfo = userRpc.getUserInfo("admin");
        log.info("testFeign.userInfo:{}", userInfo);
    }

}
