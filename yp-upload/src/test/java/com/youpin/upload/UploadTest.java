package com.youpin.upload;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.youpin.upload.config.COSProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/8 22:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UploadTest {
    @Autowired
    private COSClient cosClient;
    @Autowired
    private COSProperties cosProperties;
    @Test
    public void uploadTest(){
//        // 1 初始化用户身份信息（secretId, secretKey）。
//        String secretId = "AKIDcKlIXWKgy3vc4Jj9tNblgW8UaPJxpZj8";
//        String secretKey = "MUPecJmyuZzVs36vU8VeWLuCC5hPHxSS";
//        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
//        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
//        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
//        Region region = new Region("ap-beijing");
//        ClientConfig clientConfig = new ClientConfig(region);
//        // 3 生成 cos 客户端。
//        COSClient cosClient = new COSClient(cred, clientConfig);
//        log.info(""+cosClient);

        try {
            // 指定要上传的文件
            File localFile = new File("D:\\ceshi.jpg");
            // 指定要上传到的存储桶
            String bucketName = "youpin-1259417918";
            UUID uuid = UUID.randomUUID();
            // 指定要上传到 COS 上对象键
            String key = uuid.toString().replace("-","")+".jpg";
            PutObjectRequest putObjectRequest = new PutObjectRequest(cosProperties.getBucketName(), key, localFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            log.info(putObjectRequest+"");
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
        }

    }
}
