package com.gabriellopesjds.cooperativism.exception;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Arrays;

@Getter
public class BusinessException extends RuntimeException {

    private final String code;
    private final Object[] args;

    private BusinessException(Throwable cause, String code, Object... args) {
        super(code + " - " + Arrays.toString(args), cause);
        Assert.hasLength(code, "You cannot create a BusinessException with an empty or uninformed code");
        this.code = code;
        this.args = args;
    }

    public BusinessException(String code, Object... args) {
        this((Throwable)null, code, args);
    }

}
