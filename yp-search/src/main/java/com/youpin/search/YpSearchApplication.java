package com.youpin.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/20 9:21
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
public class YpSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(YpSearchApplication.class,args);
    }
}
