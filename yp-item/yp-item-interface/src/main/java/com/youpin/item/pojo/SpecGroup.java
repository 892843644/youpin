package com.youpin.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author ：cjy
 * @description ：规格参数的分组表，每个商品分类下有多个规格参数组
 * @CreateTime ：Created in 2019/9/10 8:33
 */
@TableName("tb_spec_group")
@Data
public class SpecGroup {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String name;//规格组的名称
    private Long cid;//商品分类id，一个分类下有多个规格组

}
