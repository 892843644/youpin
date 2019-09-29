package com.youpin.search.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BrandClientTest {
    @Autowired
    private BrandClient brandClient;

    @Test
    public void queryByIds(){
        brandClient.queryBrandByIds(Arrays.asList(76L,77L));
    }
}