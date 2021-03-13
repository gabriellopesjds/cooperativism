package com.gabriellopesjds.cooperativism.rest.exceptionhandler;

import com.gabriellopesjds.cooperativism.rest.dto.BaseResponse;
import com.gabriellopesjds.cooperativism.rest.dto.ErrorDetailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MethodArgumentNotValidExceptionHandler extends AbstractExceptionHandler<MethodArgumentNotValidException> {

    @Override
    public ResponseEntity<BaseResponse<Object>> handleException(MethodArgumentNotValidException exception) {
        List<ErrorDetailResponse> errorDetailResponseList =
            exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ErrorDetailResponse.builder()
                                .field(error.getField())
                                .message(String.format("Field %s %s", error.getField(), error.getDefaultMessage()))
                                .build())
                .collect(Collectors.toList());

        return handleErrorModelResponse(HttpStatus.BAD_REQUEST, "Argument Not Valid", errorDetailResponseList);
    }

}
