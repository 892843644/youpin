package com.youpin.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author ：cjy
 * @description ：cos配置属性类 使用ConfigurationProperties注解可将配置文件（yml/properties）中指定前缀的配置转为bean
 * @CreateTime ：Created in 2019/9/8 23:39
 */
@Data
@ConfigurationProperties(prefix = "qcloud")
public class COSProperties {
    // 初始化用户身份信息 前往密钥管理查看
    private   String secretId;
    // 初始化用户身份信息 前往密钥管理查看
    private String secretKey;
    // 指定要上传到的存储桶
    private String bucketName;
    //指定要上传的地区名称 去控制台查询
    private String regionName;
}
