package com.gabriellopesjds.cooperativism.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClientErrorResponseException extends BusinessException {

    private final HttpStatus httpStatus;

    public ClientErrorResponseException(HttpStatus httpStatus, String code, Object... args) {
        super(code, args);
        this.httpStatus = httpStatus;
    }

}
