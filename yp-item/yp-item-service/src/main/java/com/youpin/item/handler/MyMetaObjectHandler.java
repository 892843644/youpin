package com.youpin.item.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/16 11:07
 */
@Component
public class MyMetaObjectHandler  implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setInsertFieldValByName("createTime",new Date(), metaObject);
        this.setFieldValByName("lastUpdateTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("lastUpdateTime", new Date(), metaObject);
    }
}
