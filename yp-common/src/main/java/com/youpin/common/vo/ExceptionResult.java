package com.youpin.common.vo;

import com.youpin.common.enums.ExceptionEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/6 20:45
 */
@Data
@NoArgsConstructor
public class ExceptionResult {
    private int status;
    private String message;
    private Long timestamp;

    public ExceptionResult(ExceptionEnum em) {
        this.status = em.getCode();
        this.message = em.getMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
