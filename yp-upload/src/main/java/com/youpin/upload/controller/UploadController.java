package com.youpin.upload.controller;

import com.youpin.upload.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/7 15:56
 */
@RestController
@RequestMapping("upload")
@Slf4j
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file){
        String url=uploadService.uploadImage(file);
        //成功返回图片地址
        return ResponseEntity.ok(url);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteImage(@RequestParam("imageUrl") String imageUrl) {
        uploadService.deleteImage(imageUrl);
        return ResponseEntity.ok("ok");
    }

}
