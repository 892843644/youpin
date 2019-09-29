package com.youpin.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youpin.common.enums.ExceptionEnum;
import com.youpin.common.exception.YpException;
import com.youpin.item.client.UploadClient;
import com.youpin.item.mapper.BrandMapper;
import com.youpin.item.pojo.Brand;
import com.youpin.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;


/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/7 9:36
 */
@Service
@Transactional
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {
    
    @Autowired
    private UploadClient uploadClient;

    /**
     * 保存品牌
     *
     * @param brand
     * @param cids
     */
    @Override
    public void saveBrand(Brand brand, List<Long> cids) {

        //保存品牌
        boolean result = this.save(brand);
        if (result == false) {
            throw new YpException(ExceptionEnum.BRAND_SAVE_ERROR);
        }

        //保存品牌和分类中间表数据
        for(Long cid:cids){
            int count = baseMapper.insertCategoryBrand(cid, brand.getId());
            if(count!=1){//保存失败
                throw new YpException(ExceptionEnum.BRAND_SAVE_ERROR);
            }
        }

    }

    /**
     * 修改品牌
     * @param brand
     * @param cids
     */
    @Override
    public void updateBrand(Brand brand, List<Long> cids) {
        //修改品牌
        boolean result = this.updateById(brand);
        if (result == false) {
            throw new YpException(ExceptionEnum.BRAND_UPDATE_ERROR);
        }

        //删除品牌原来对应的分类id中间表数据
        int count = baseMapper.deleteByBrandId(brand.getId());
        if(count==0){
            throw new YpException(ExceptionEnum.BRAND_UPDATE_ERROR);
        }

        //保存新的品牌和分类中间表数据
        for(Long cid:cids){
             count = baseMapper.insertCategoryBrand(cid, brand.getId());
            if(count!=1){//保存失败
                throw new YpException(ExceptionEnum.BRAND_UPDATE_ERROR);
            }
        }

    }

    /**
     *根据品牌id删除 并删除对应图片
     * @param bid
     * @param imageUrl
     */
    @Override
    public void deleteByBrandId(Long bid, String imageUrl) {
        //删除商品数据
        boolean flag = this.removeById(bid);
        if(flag==false){
            throw new YpException(ExceptionEnum.BRAND_DELETE_ERROR);
        }

        //删除品牌分类中间表数据
        baseMapper.deleteByBrandId(bid);

        //删除cos服务器上的照片数据
        uploadClient.deleteImage(imageUrl);

    }

    /**
     * 根据分类id查询品牌
     * @param cid
     * @return
     */
    @Override
    public List<Brand> queryBrandByCid(Long cid) {
        List<Brand> brands = baseMapper.queryBrandByCid(cid);
        if(CollectionUtils.isEmpty(brands)){
            throw new YpException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        return brands;
    }
}