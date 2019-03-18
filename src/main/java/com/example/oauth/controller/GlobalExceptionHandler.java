package com.example.oauth.controller;

import com.example.oauth.http.RestResp;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Administrator
 */
@ControllerAdvice
public class
GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object errorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        if (e instanceof BindException || e instanceof MethodArgumentNotValidException) {
            return handlerArgsValid(e);
        }
        return RestResp.ok("500");
    }


    /**
     * 参数校验处理
     *
     * @param e 异常类
     * @return 对象
     */
    private Object handlerArgsValid(Exception e) {
        BindingResult bindingResult = null;
        if (e instanceof BindException) {
            bindingResult = ((BindException) e).getBindingResult();
        } else if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        }
        List<FieldError> errors = bindingResult.getFieldErrors();
        String message = "";
        if (errors != null) {
            for (FieldError error : errors) {
                message = error.getDefaultMessage();
                break;
            }
        }
        return RestResp.ok(message);
    }

}
