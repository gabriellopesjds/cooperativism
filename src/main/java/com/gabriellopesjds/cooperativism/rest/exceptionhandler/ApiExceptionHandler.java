package com.gabriellopesjds.cooperativism.rest.exceptionhandler;

import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.rest.dto.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApiExceptionHandler {

    private final BusinessExceptionHandler businessExceptionHandler;
    private final HttpMessageNotReadableExceptionHandler httpMessageNotReadableExceptionHandler;
    private final MethodArgumentNotValidExceptionHandler methodArgumentNotValidExceptionHandler;
    private final ConstraintViolationExceptionHandler constraintViolationExceptionHandler;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException exception){
        return httpMessageNotReadableExceptionHandler.handleException(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception){
        return methodArgumentNotValidExceptionHandler.handleException(exception);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<Object>> handleBusinessException(BusinessException exception){
        return businessExceptionHandler.handleException(exception);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse<Object>> handleBusinessException(ConstraintViolationException exception){
        return constraintViolationExceptionHandler.handleException(exception);
    }
}
