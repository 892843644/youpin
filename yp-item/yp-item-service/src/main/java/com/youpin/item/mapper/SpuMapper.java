package com.youpin.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youpin.item.pojo.Spu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SpuMapper extends BaseMapper<Spu> {


    @Select(" SELECT brand_id,sub_title FROM tb_spu WHERE id=#{id}")
    Spu queryBrandIdAndSubTitleById(@Param("id") Long id);
}
