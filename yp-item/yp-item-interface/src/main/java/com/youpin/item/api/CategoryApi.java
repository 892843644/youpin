package com.youpin.item.api;

import com.youpin.item.pojo.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/23 8:49
 */
public interface CategoryApi {
    /**
     * 根据id查询商品分类
     * @param ids
     * @return
     */
    @GetMapping("category/list/ids")
    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);
}
