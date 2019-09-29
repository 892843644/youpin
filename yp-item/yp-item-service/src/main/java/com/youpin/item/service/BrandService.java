package com.youpin.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youpin.item.pojo.Brand;

import java.util.List;

public interface BrandService extends IService<Brand> {

    /**
     * 保存品牌
     * @param brand
     * @param cids
     */
    void saveBrand(Brand brand, List<Long> cids);

    void updateBrand(Brand brand, List<Long> cids);

    void deleteByBrandId(Long bid, String imageUrl);

    List<Brand> queryBrandByCid(Long cid);
}
