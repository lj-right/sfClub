package jingdiansuifeng.oss.config;

import jingdiansuifeng.oss.adapter.StorageAdapter;
import jingdiansuifeng.oss.adapter.AliStorageAdapter;
import jingdiansuifeng.oss.adapter.MinioStorageAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RefreshScope
public class StorageConfig {

    @Value("${storage.service.type}")
    private String storageType;

    @Bean
    @RefreshScope
    public StorageAdapter storageService(){
        if ("minio".equals(storageType)) {
            return new MinioStorageAdapter();
        } else if (("aliyun").equals(storageType)) {
            return new AliStorageAdapter();
        } else {
            throw new IllegalArgumentException("未找到对应的文件存储处理器");
        }
    }

}
