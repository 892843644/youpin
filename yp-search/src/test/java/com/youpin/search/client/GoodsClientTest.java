package com.youpin.search.client;

import com.youpin.common.utils.JsonUtils;
import com.youpin.common.vo.PageResult;
import com.youpin.item.pojo.Spu;
import com.youpin.item.request.SpuPageRequest;
import com.youpin.item.vo.SpuVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GoodsClientTest {
    @Autowired
    private GoodsClient goodsClient;

    @Test
    public void test1(){
        SpuPageRequest spuPageRequest = new SpuPageRequest();
        spuPageRequest.setKey("小");

        PageResult<Spu> pageResult = goodsClient.querySpuByPageApi(spuPageRequest);
        log.info("【pageResult】={}",pageResult);
    }
    
}