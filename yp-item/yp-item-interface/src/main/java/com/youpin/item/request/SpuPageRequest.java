package com.youpin.item.request;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/11 10:26
 */
@Data
public class SpuPageRequest {
    private Integer page=1;
    private String key;
    private Integer rows=5;
    private Boolean saleable;

}
