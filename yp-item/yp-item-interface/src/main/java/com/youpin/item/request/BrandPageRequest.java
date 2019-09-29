package com.youpin.item.request;

import lombok.Data;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/7 9:51
 */
@Data
public class BrandPageRequest {
    private Integer page=1;
    private Integer rows=5;
    private String sortBy;
    private Boolean desc=false;
    private String key;

}
