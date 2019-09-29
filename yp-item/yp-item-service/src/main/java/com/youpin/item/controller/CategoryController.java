package com.youpin.item.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youpin.common.enums.ExceptionEnum;
import com.youpin.common.exception.YpException;
import com.youpin.item.pojo.Category;
import com.youpin.item.service.CategoryService;
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
 * @CreateTime ：Created in 2019/9/6 19:44
 */
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 通过分类父类id查询
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryListByParentId(@RequestParam("pid")Long pid){
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",pid);
        List<Category> list = categoryService.list(queryWrapper);
        if(CollectionUtils.isEmpty(list)){
            throw new YpException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }

        return ResponseEntity.ok(list);
    }

    /**
     * 根据品牌id查询商品分类
     * @param bid
     * @return
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryByBrandId(@PathVariable("bid")Long bid){
        List<Category> categories = categoryService.queryByBrandId(bid);
        return ResponseEntity.ok(categories);
    }

    /**
     * 根据id查询商品分类
     * @param ids
     * @return
     */
    @GetMapping("list/ids")
    public ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids") List<Long> ids){
        List<Category> categories = (List<Category>) categoryService.listByIds(ids);
        return ResponseEntity.ok(categories);
    }

}
