package com.youpin.item.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/9 20:54
 */
@FeignClient("upload-service")
public interface UploadClient {

    @DeleteMapping("/upload")
    public ResponseEntity<String> deleteImage(@RequestParam("imageUrl") String imageUrl);
}
