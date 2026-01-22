package com.test.hex.cinehex.infrastructure.adapter.in.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T data;
    private String path;
}
