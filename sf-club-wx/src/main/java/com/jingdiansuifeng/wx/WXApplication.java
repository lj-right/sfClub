package com.jingdiansuifeng.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * wx服务启动器
 *
 */

@SpringBootApplication
@ComponentScan("com.jingdiansuifeng")
public class WXApplication {
    public static void main(String[] args) {
        SpringApplication.run(WXApplication.class);

    }
}
