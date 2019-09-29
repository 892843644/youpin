package com.youpin.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/7 9:31
 */
@TableName("tb_brand")
@Data
public class Brand implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String name;// 品牌名称
    private String image;// 品牌图片
    private Character letter;//品牌的首字母
}
