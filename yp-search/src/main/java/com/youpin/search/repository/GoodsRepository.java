package com.youpin.search.repository;

import com.youpin.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/23 10:28
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
