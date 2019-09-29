package com.youpin.search.repository;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.youpin.common.vo.PageResult;
import com.youpin.item.pojo.Spu;
import com.youpin.item.request.SpuPageRequest;
import com.youpin.item.vo.SpuVo;
import com.youpin.search.client.GoodsClient;
import com.youpin.search.pojo.Goods;
import com.youpin.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GoodsRepositoryTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;

    @Test
    public void testCreateIndex(){
        template.createIndex(Goods.class);
        template.putMapping(Goods.class);
    }
    @Test
    public void loadData(){
        int size=0;
        int page=1;

      do {
          SpuPageRequest spuPageRequest = new SpuPageRequest();
          spuPageRequest.setPage(page);
          spuPageRequest.setRows(100);
          spuPageRequest.setSaleable(true);
          //查询spu消息
          PageResult<Spu> pageResult = goodsClient.querySpuByPageApi(spuPageRequest);
          List<Spu> spus = pageResult.getItems();
          if(CollectionUtils.isEmpty(spus)){
              break;
          }
          //构建成goods
          List<Goods> goodsList = spus.stream().map(searchService::buildGoods).collect(Collectors.toList());
          //存入索引库
          goodsRepository.saveAll(goodsList);

          //翻页
          page++;
          size=spus.size();
      }while (size==100);

    }
}