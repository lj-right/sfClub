package com.jingdiansuifeng.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 刷题微服务启动类
 *
 */

@SpringBootApplication
@ComponentScan("com.jingdiansuifeng")
@MapperScan("com.jingdiansuifeng.auth.**.mapper")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class);

    }
}
