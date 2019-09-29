package com.youpin.common.exception;

import com.youpin.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/6 20:35
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class YpException extends RuntimeException{
    private ExceptionEnum exceptionEnum;


}
