package com.youpin.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/11 9:48
 */
@Data
@TableName("tb_spu_detail")
public class SpuDetail {
    @TableId
    private Long spuId;// 对应的SPU的id
    private String description;// 商品描述
    private String genericSpec;//通用规格参数数据
    private String specialSpec;//特有规格参数及可选值信息，json格式
    private String packingList;// 包装清单
    private String afterService;// 售后服务
}
