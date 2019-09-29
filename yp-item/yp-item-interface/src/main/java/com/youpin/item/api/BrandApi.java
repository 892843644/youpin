package com.youpin.item.api;

import com.youpin.item.pojo.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/23 8:56
 */
public interface BrandApi {

    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @GetMapping("/brand/{id}")
    Brand queryBrandById(@PathVariable("id")Long id);

    /**
     * 根据ids查询品牌集合
     * @param ids
     * @return
     */
    @GetMapping("/brand/list")
    List<Brand> queryBrandByIds(@RequestParam("ids") List<Long> ids);
}
