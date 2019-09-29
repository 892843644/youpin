package com.youpin.item.vo;

import com.youpin.item.pojo.Sku;
import com.youpin.item.pojo.SpuDetail;
import lombok.Data;

import java.util.List;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/17 9:18
 */
@Data
public class GoodsVo {
    private String subTitle;
    private Long brandId;
    private List<CategoryVo> categories;
    private SpuDetail spuDetail;
    private List<Sku> skus;
}
