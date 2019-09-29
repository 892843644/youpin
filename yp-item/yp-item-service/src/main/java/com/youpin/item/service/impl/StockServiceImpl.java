package com.youpin.item.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youpin.item.mapper.StockMapper;
import com.youpin.item.pojo.Stock;
import com.youpin.item.service.StockService;
import org.springframework.stereotype.Service;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/16 11:45
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper,Stock> implements StockService {
}
