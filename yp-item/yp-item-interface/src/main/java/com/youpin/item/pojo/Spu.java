package com.youpin.item.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Author ：cjy
 * @description ：spu表，该表描述的是一个抽象性的商品，比如 iphone8
 * @CreateTime ：Created in 2019/9/11 9:37
 */
@TableName("tb_spu")
@Data
public class Spu {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @NotEmpty(message ="商品名称不能为空" )
    private String name;//商品名称

    @NotEmpty(message ="商品子标题不能为空" )
    private String subTitle;//子标题

    @NotNull(message ="品牌id不能为空" )
    private Long brandId;//品牌id

    @NotNull (message ="分类不能为空" )
    private Long cid1;// 1级类目

    @NotNull(message ="分类不能为空" )
    private Long cid2;// 2级类目

    @NotNull(message ="分类不能为空" )
    private Long cid3;// 3级类目

    private Boolean saleable;// 是否上架
    private Boolean valid;// 是否有效，逻辑删除用

    @TableField(fill = FieldFill.INSERT)//自动注入时间
    private Date createTime;// 创建时间

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateTime;// 最后修改时间

    @NotNull(message ="sku属性不能为空" )
    @TableField(exist = false)//数据库忽略此字段
    private List<Sku> skus;

    @TableField(exist = false)
    private SpuDetail spuDetail;
}
