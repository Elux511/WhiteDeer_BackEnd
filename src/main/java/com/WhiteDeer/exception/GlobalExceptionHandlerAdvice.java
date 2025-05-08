package com.WhiteDeer.exception;

import com.WhiteDeer.mapper.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice//异常统一处理
public class GlobalExceptionHandlerAdvice {
    Logger log= ILoggerFactory.getLogger(GlobalExceptionhandlerAdvice.class);
    @ExceptionHandler({Exception.class})
    public ResponseMessage handlerException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        //记录日志（异常）
        log.error("统一异常",e);
        return new ResponseMessage(500,"error",null);//返回500为后端异常//问题：如何显示异常
    }
}
