package com.youpin.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/6 0:05
 */
@SpringBootApplication
@EnableDiscoveryClient
public class YpGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(YpGateWayApplication.class,args);
    }
}
