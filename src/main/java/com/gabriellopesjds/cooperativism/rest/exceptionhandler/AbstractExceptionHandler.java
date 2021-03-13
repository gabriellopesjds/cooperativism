package com.gabriellopesjds.cooperativism.rest.exceptionhandler;

import com.gabriellopesjds.cooperativism.rest.dto.BaseResponse;
import com.gabriellopesjds.cooperativism.rest.dto.ErrorDetailResponse;
import com.gabriellopesjds.cooperativism.rest.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class AbstractExceptionHandler<T extends Exception> {
    public abstract ResponseEntity<BaseResponse<Object>> handleException(T exception);

    protected ResponseEntity<BaseResponse<Object>> handleErrorModelResponse(HttpStatus status, String title, List<ErrorDetailResponse> details) {
        return ResponseEntity
            .status(status)
            .body(BaseResponse.withError(buildErrorResponse(title, details)));
    }

    private ErrorResponse buildErrorResponse(String title, List<ErrorDetailResponse> details) {
        return ErrorResponse.builder()
            .title(title)
            .details(details)
            .build();
    }
}
