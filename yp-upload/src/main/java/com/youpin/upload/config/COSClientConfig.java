package com.youpin.upload.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author ：cjy
 * @description ：COSClient 是调用 COS API 接口的对象
 * @CreateTime ：Created in 2019/9/8 23:16
 */
@Configuration
@EnableConfigurationProperties(COSProperties.class)
public class COSClientConfig {
    @Autowired
    private COSProperties cosProperties;

    @Bean
    public COSClient cosClient(){
        // 1 初始化用户身份信息（secretId, secretKey）。
//        String secretId = "AKIDcKlIXWKgy3vc4Jj9tNblgW8UaPJxpZj8";
//        String secretKey = "MUPecJmyuZzVs36vU8VeWLuCC5hPHxSS";
        COSCredentials cred = new BasicCOSCredentials(cosProperties.getSecretId(),cosProperties.getSecretKey());
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(cosProperties.getRegionName());
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }
}
