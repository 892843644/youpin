package com.youpin.item.controller;

import com.youpin.common.vo.PageResult;
import com.youpin.item.pojo.Sku;
import com.youpin.item.pojo.Spu;
import com.youpin.item.pojo.SpuDetail;
import com.youpin.item.request.SaleableRequest;
import com.youpin.item.request.SpuPageRequest;
import com.youpin.item.service.GoodsService;
import com.youpin.item.vo.CategoryVo;
import com.youpin.item.vo.GoodsVo;
import com.youpin.item.vo.SpuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/11 10:09
 */
@RestController
@RequestMapping
@Slf4j
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 分页查询spu
     * @param spuPageRequest
     * @return
     */
    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<SpuVo>> querySpuByPage(SpuPageRequest spuPageRequest){
        return ResponseEntity.ok(goodsService.querySpuByPage(spuPageRequest));
    }
    /**
     * feign调用分页查询spu
     * @param spuPageRequest
     * @return
     */

    @PostMapping("/spu/page")
    public PageResult<Spu> querySpuByPageApi(@RequestBody SpuPageRequest spuPageRequest){
        return goodsService.querySpuByPageApi(spuPageRequest);
    }


    /**
     * 根据spuId查询前端需要的商品数据
     * @param spuId
     * @return
     */
    @GetMapping("/spu/goods/{spuId}")
    public ResponseEntity<GoodsVo> queryGoodsById(@PathVariable("spuId")Long spuId){
        GoodsVo goodsVo = goodsService.queryGoodsById(spuId);
        return ResponseEntity.ok(goodsVo);
    }

    /**
     * 保存商品
     * @param spu
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody @Valid Spu spu){
        goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 商品修改
     * @param spu
     * @return
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody @Valid Spu spu){
        goodsService.updateGoods(spu);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 根据spuId修改上下架状态
     * @param saleable
     * @param saleable
     * @return
     */
    @PutMapping("/spu/saleable")
    public ResponseEntity<Void> changeSaleable(@RequestBody SaleableRequest saleable){
        goodsService.changeSaleable(saleable);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 根据id删除商品
     * @param id
     * @return
     */
    @DeleteMapping("/spu/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("id")Long id){
        goodsService.deleteGoods(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据spuId查询商品详情
     * @param spuId
     * @return
     */
    @GetMapping("/spu/detail/{id}")
    public ResponseEntity<SpuDetail> queryDetailById(@PathVariable("id")Long spuId){
        return ResponseEntity.ok(goodsService.queryDetailById(spuId));
    }

    /**
     * 根据spuid查询所有的sku
     * @param id
     * @return
     */
    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long id){
        return ResponseEntity.ok(goodsService.querySkuBySpuId(id));
    }
}
