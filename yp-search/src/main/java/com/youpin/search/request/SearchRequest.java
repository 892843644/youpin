package com.youpin.search.request;

import lombok.Data;

import java.util.Map;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/24 11:22
 */
@Data
public class SearchRequest {
    private String key;
    private Integer page;
    private Map<String,String> filter;//过滤字段
    private static final int DEFAULT_SIZE=20;
    private static final int DEFAULT_PAGE=1;

    public Integer getPage() {
        if(page==null){
            return DEFAULT_PAGE;
        }
        //获取页码校验 以防传负数
        return Math.max(DEFAULT_PAGE,page);
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }
}
