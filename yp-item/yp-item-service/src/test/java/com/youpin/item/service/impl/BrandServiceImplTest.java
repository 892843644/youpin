package com.youpin.item.service.impl;


import com.youpin.item.pojo.Brand;
import com.youpin.item.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BrandServiceImplTest {
    @Autowired
    private BrandService brandService;

    @Test
    public void saveBrand() {
        Brand brand = new Brand();
        brand.setName("夏林");
        brandService.saveBrand(brand, Arrays.asList(3L,79L));
        System.out.println(brand);
    }
}
