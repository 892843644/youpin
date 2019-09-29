package com.youpin.item.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youpin.common.enums.ExceptionEnum;
import com.youpin.common.exception.YpException;
import com.youpin.common.vo.PageResult;
import com.youpin.item.pojo.Brand;
import com.youpin.item.request.BrandPageRequest;
import com.youpin.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/7 9:42
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 根据分页查询品牌
     * @param pageRequest
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(BrandPageRequest pageRequest){
        //where name like '%x%' or letter == 'x' order by id desc
        IPage<Brand> page=new Page(pageRequest.getPage(),pageRequest.getRows());
        QueryWrapper<Brand> queryWrapper = new QueryWrapper();
        queryWrapper.like("name",pageRequest.getKey());
        queryWrapper.or().eq("letter",pageRequest.getKey().toUpperCase());
        queryWrapper.orderByDesc(pageRequest.getDesc(),pageRequest.getSortBy());
        page = brandService.page(page, queryWrapper);

        if(CollectionUtils.isEmpty(page.getRecords())){ //品牌数据为空抛出异常
            throw new YpException(ExceptionEnum.BRAND_NOT_FOUND);
        }

        return ResponseEntity.ok(new PageResult<Brand>(page.getTotal(),page.getPages(),page.getRecords()));
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 更改品牌
     * @param brand
     * @param cids
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        brandService.updateBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据品牌id删除
     * @param bid
     * @param imageUrl
     * @return
     */
    @DeleteMapping("/{bid}")
    public ResponseEntity<Void> deleteByBrandId(@PathVariable("bid") Long bid,@RequestParam("imageUrl")String imageUrl){
        brandService.deleteByBrandId(bid,imageUrl);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据分类id查询品牌
     * @param cid
     * @return
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable("cid")Long cid){
        List<Brand> list = brandService.queryBrandByCid(cid);
        return ResponseEntity.ok(list);
    }

    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id")Long id){
        return ResponseEntity.ok(brandService.getById(id));
    }

    /**
     * 根据ids查询品牌集合
     * @param ids
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<Brand>> queryBrandByIds(@RequestParam("ids")List<Long> ids){
        List<Brand> brands = (List<Brand>) brandService.listByIds(ids);
        if(CollectionUtils.isEmpty(brands)){
            throw new YpException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return ResponseEntity.ok(brands);
    }
}
