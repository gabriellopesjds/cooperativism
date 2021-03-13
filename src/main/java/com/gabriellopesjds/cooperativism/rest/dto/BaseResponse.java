package com.gabriellopesjds.cooperativism.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(value = Include.NON_NULL)
@Setter
@Getter
public class BaseResponse<T> {

	private T data;
	private ErrorResponse error;

	public static <T> BaseResponse<T> withData(T data) {
		BaseResponse<T> baseResponse = new BaseResponse<>();
		baseResponse.setData(data);
		return baseResponse;
	}

	public static BaseResponse<Object> withError(ErrorResponse error) {
		BaseResponse<Object> baseResponse = new BaseResponse<>();
		baseResponse.setError(error);
		return baseResponse;
	}

}
