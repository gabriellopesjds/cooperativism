package com.gabriellopesjds.cooperativism.exception;

public class ServerErrorResponseException extends BusinessException{

    public ServerErrorResponseException(String code, Object... args) {
        super(code, args);
    }

}
