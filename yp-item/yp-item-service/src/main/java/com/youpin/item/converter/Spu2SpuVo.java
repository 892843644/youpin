package com.youpin.item.converter;

import com.youpin.item.pojo.Spu;
import com.youpin.item.service.BrandService;
import com.youpin.item.service.CategoryService;
import com.youpin.item.vo.SpuVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/11 11:19
 */
public class Spu2SpuVo {

    @Autowired
    private static CategoryService categoryService;
    @Autowired
    private static BrandService brandService;

    public static List<SpuVo> convert(List<Spu> spuList){
        List<SpuVo> SpuList = spuList.stream().map(e -> convert(e)).collect(Collectors.toList());
        return SpuList;
    }
    public static SpuVo convert(Spu spu){

        SpuVo spuVo = new SpuVo();
        //把spu属性拷贝到spuVo
        BeanUtils.copyProperties(spu,spuVo);
        return spuVo;
    }
}
