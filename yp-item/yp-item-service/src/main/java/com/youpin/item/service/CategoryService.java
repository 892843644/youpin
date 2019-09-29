package com.youpin.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youpin.item.pojo.Category;

import java.util.List;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/6 19:41
 */
public interface CategoryService extends IService<Category> {
    List<Category> queryByBrandId(Long bid);
}
