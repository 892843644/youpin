package com.youpin.item.controller;

import com.youpin.item.pojo.SpecGroup;
import com.youpin.item.pojo.SpecParam;
import com.youpin.item.service.SpecificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/10 9:15
 */
@RestController
@RequestMapping("spec")
@Slf4j
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    /**
     * 通过分类id查询规格组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid")Long cid){
       return ResponseEntity.ok(specificationService.queryGroupByCategoryId(cid));
    }

    /**
     * 查询规格参数名
     * @param gid
     * @param cid
     * @param searching
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParamByGid(@RequestParam(value = "gid",required = false)Long gid,
                                                           @RequestParam(value = "cid",required = false)Long cid,
                                                           @RequestParam(value = "searching",required = false)Boolean searching){

        return ResponseEntity.ok(specificationService.queryParamList(gid,cid,searching));
    }



    /**
     * 添加新的规格组
     * @param specGroup
     * @return
     */
    @PostMapping("group")
    public ResponseEntity<Void> saveGroup(@RequestBody SpecGroup specGroup){
        specificationService.saveOrUpdateGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 更新规格组
     * @param specGroup
     * @return
     */
    @PutMapping("group")
    public ResponseEntity<Void> updateGroup(@RequestBody SpecGroup specGroup){
        specificationService.saveOrUpdateGroup(specGroup);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据id删除规格组
     * @param id
     * @return
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id")Long id){
        specificationService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }


    /**
     * 添加新的商品参数
     * @param specParam
     * @return
     */
    @PostMapping("param")
    public ResponseEntity<Void> saveParam(@RequestBody SpecParam specParam){
        specificationService.saveOrUpdateParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 更新商品参数
     * @param specParam
     * @return
     */
    @PutMapping("param")
    public ResponseEntity<Void> updateParam(@RequestBody SpecParam specParam){
        specificationService.saveOrUpdateParam(specParam);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据id删除商品参数
     * @param id
     * @return
     */
    @DeleteMapping("param/{id}")
    public ResponseEntity<Void> deleteParam(@PathVariable("id")Long id){
        specificationService.deleteParam(id);
        return ResponseEntity.ok().build();
    }
}
