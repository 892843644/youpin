package com.youpin.item.service.impl;

import com.youpin.item.service.GoodsService;
import com.youpin.item.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GoodsServiceImplTest {
    @Autowired
    private GoodsService goodsService;

    @Test
    public void queryGoodsById() {
        GoodsVo goodsVo = goodsService.queryGoodsById(204L);
        log.info(goodsVo+"");
    }

}