package com.youpin.item.mapper;

import com.youpin.item.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BrandMapperTest {

    @Autowired
    private BrandMapper brandMapper;

    @Test
    public void insertCategoryBrand() {
        int i = brandMapper.insertCategoryBrand(75L, 31L);
        log.info(i+"");
    }
}