package com.youpin.search.client;

import com.youpin.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/20 15:03
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {

}
