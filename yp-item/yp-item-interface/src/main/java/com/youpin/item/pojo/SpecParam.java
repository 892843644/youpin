package com.youpin.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author ：cjy
 * @description ：规格参数组下的参数名
 * @CreateTime ：Created in 2019/9/10 8:51
 */
@TableName("tb_spec_param")
@Data
public class SpecParam {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String name;//参数名
    private Long cid;//商品分类id
    private Long groupId;//规格参数组id
    @TableField("`numeric`")
    private Boolean numeric;//是否是数字类型参数，true或false
    private String unit;//数字类型参数的单位，非数字类型可以为空
    private Boolean generic;//是否是sku通用属性，true或false
    private Boolean searching;//是否用于搜索过滤，true或false
    private String segments;//数值类型参数，如果需要搜索，则添加分段间隔值，如CPU频率间隔：0.5-1.0
}
