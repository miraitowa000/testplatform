package com.testplatform.exception;

import com.testplatform.common.ResultVO;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResultVO(400, String.join(", ", errors), null);
    }

    @ExceptionHandler(BindException.class)
    public ResultVO handleBindException(BindException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResultVO(400, String.join(", ", errors), null);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResultVO handleRuntimeException(RuntimeException ex) {
        return new ResultVO(500, ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ResultVO handleException(Exception ex) {
        return new ResultVO(500, "系统错误", null);
    }
}