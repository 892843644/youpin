package com.youpin.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youpin.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/7 9:35
 */
@Repository
public interface BrandMapper extends BaseMapper<Brand> {

    @Insert("insert into tb_category_brand (category_id,brand_id) values (#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid") Long cid,@Param("bid") Long bid);

    @Delete("DELETE FROM tb_category_brand WHERE brand_id=#{bid}")
    int deleteByBrandId(@Param("bid")Long bid);

    @Select("SELECT b.id,b.name,b.image,b.letter FROM tb_brand b INNER JOIN tb_category_brand cb on b.id=cb.brand_id where cb.category_id=#{cid}")
    List<Brand> queryBrandByCid(@Param("cid") Long cid);
}
