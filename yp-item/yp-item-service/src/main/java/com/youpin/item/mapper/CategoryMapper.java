package com.youpin.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youpin.item.pojo.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/6 19:36
 */
public interface CategoryMapper  extends BaseMapper<Category> {

    /**
     *    根据品牌id查询商品分类
     * @param bid
     * @return
     */
    @Select("SELECT * FROM tb_category WHERE id IN (SELECT category_id FROM tb_category_brand WHERE brand_id = #{bid})")
    List<Category> queryByBrandId(Long bid);
}
