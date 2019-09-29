package com.youpin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/6 9:23
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = {"com.youpin.item.mapper"})
public class YpItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(YpItemApplication.class,args);
    }
}
