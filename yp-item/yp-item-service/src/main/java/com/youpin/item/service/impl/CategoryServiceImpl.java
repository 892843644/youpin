package com.youpin.item.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youpin.common.enums.ExceptionEnum;
import com.youpin.common.exception.YpException;
import com.youpin.item.mapper.CategoryMapper;
import com.youpin.item.pojo.Category;
import com.youpin.item.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/6 20:01
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     *   根据品牌id查询商品分类
     * @param bid
     * @return
     */
    @Override
    public List<Category> queryByBrandId(Long bid) {
        List<Category> categories = baseMapper.queryByBrandId(bid);
        if(CollectionUtils.isEmpty(categories)){ //如果为空抛出异常
            throw new YpException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return categories;
    }
}
