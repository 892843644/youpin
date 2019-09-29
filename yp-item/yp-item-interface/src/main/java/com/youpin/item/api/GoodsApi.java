package com.youpin.item.api;

import com.youpin.common.vo.PageResult;
import com.youpin.item.pojo.Sku;
import com.youpin.item.pojo.Spu;
import com.youpin.item.pojo.SpuDetail;
import com.youpin.item.request.SpuPageRequest;
import com.youpin.item.vo.SpuVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/23 8:49
 */
public interface GoodsApi {
    /**
     * 根据spuId查询商品详情
     * @param spuId
     * @return
     */
    @GetMapping("/spu/detail/{id}")
    SpuDetail queryDetailById(@PathVariable("id")Long spuId);

    /**
     * 根据spuId查询所有的sku
     * @param id
     * @return
     */
    @GetMapping("/sku/list")
    List<Sku> querySkuBySpuId(@RequestParam("id") Long id);

    @PostMapping("/spu/page")
    PageResult<Spu> querySpuByPageApi(@RequestBody SpuPageRequest spuPageRequest);
}
