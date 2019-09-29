package com.youpin.search.client;

import com.youpin.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {

}
