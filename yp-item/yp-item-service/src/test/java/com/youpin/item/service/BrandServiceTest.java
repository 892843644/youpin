package com.youpin.item.service;

import com.youpin.item.pojo.Brand;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BrandServiceTest {
    @Autowired
    private BrandService brandService;
    @Test
    public void test1(){
        List<Brand> brands = (List<Brand>) brandService.listByIds(Arrays.asList(1528L, 1912L));
        log.info(brands+"");
    }
}