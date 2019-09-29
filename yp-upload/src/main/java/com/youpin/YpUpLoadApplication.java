package com.youpin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/7 15:39
 */
@SpringBootApplication
@EnableDiscoveryClient
public class YpUpLoadApplication {
    public static void main(String[] args) {
        SpringApplication.run(YpUpLoadApplication.class,args);
    }
}
