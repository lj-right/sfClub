package jingdiansuifeng.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * oss服务启动器
 *
 */

@SpringBootApplication
@ComponentScan("jingdiansuifeng")
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class);

    }
}
