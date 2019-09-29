package com.youpin.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/6 19:57
 */
@Data
@TableName("tb_category")
public class Category {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private Long parentId;
    private Boolean isParent;
    private Integer sort;
}
