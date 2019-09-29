package com.youpin.item.service;

import com.youpin.common.vo.PageResult;
import com.youpin.item.pojo.Sku;
import com.youpin.item.pojo.Spu;
import com.youpin.item.pojo.SpuDetail;
import com.youpin.item.request.SaleableRequest;
import com.youpin.item.request.SpuPageRequest;
import com.youpin.item.vo.GoodsVo;
import com.youpin.item.vo.SpuVo;

import java.util.List;


/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/11 9:58
 */
public interface GoodsService{
    PageResult<SpuVo> querySpuByPage(SpuPageRequest spuPageRequest);

    void saveGoods(Spu spu);

    GoodsVo queryGoodsById(Long spuId);

    void updateGoods(Spu spu);

    void changeSaleable(SaleableRequest saleable);

    void deleteGoods(Long id);

    SpuDetail queryDetailById(Long spuId);

    List<Sku> querySkuBySpuId(Long id);

    PageResult<Spu> querySpuByPageApi(SpuPageRequest spuPageRequest);

}
