package com.youpin.common.advice;
import com.youpin.common.enums.ExceptionEnum;
import com.youpin.common.exception.YpException;
import com.youpin.common.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
public class BaseExceptionHandler {
	
    @ExceptionHandler(value = YpException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResult> error(YpException e){
        return ResponseEntity.status(e.getExceptionEnum().getCode())
                .body(new ExceptionResult(e.getExceptionEnum()));
    }
}
