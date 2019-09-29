package com.youpin.item.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/11 10:35
 */
@Data
public class SpuVo {
    private Long id;
    private String name;//商品名称
    private String cname;//一类/二类/三类 名
    private String bname;//品牌名
    private Boolean saleable;// 是否上架
}
