package com.gabriellopesjds.cooperativism.rest.exceptionhandler;

import com.gabriellopesjds.cooperativism.rest.dto.BaseResponse;
import com.gabriellopesjds.cooperativism.rest.dto.ErrorDetailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

@Component
public class ConstraintViolationExceptionHandler extends AbstractExceptionHandler<ConstraintViolationException>{

    @Override
    public ResponseEntity<BaseResponse<Object>> handleException(ConstraintViolationException exception) {

        List<ErrorDetailResponse> errorDetailResponseList =
            Arrays.stream(exception.getMessage().split(","))
                .map(String::trim)
                .map(error -> ErrorDetailResponse.builder().message(error).build())
                .collect(Collectors.toList());


        return handleErrorModelResponse(HttpStatus.BAD_REQUEST, "Constraint Violation", errorDetailResponseList);
    }

}
