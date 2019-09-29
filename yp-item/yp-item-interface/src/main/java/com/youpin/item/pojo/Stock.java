package com.youpin.item.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/16 10:02
 */
@Data
@TableName("tb_stock")
public class Stock {
    @TableId
    private Long skuId;
    private Integer seckillStock;// 秒杀可用库存
    private Integer seckillTotal;// 已秒杀数量
    private Integer stock;// 正常库存
}
