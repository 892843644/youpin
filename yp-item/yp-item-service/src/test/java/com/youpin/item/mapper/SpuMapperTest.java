package com.youpin.item.mapper;

import com.youpin.item.pojo.Spu;
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
public class SpuMapperTest {
    @Autowired
    private SpuMapper spuMapper;

    @Test
    public void queryBrandIdById() {
        Spu spu = spuMapper.queryBrandIdAndSubTitleById(2L);
        log.info(spu+"");
    }
}