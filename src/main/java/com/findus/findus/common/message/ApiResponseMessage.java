package com.findus.findus.common.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class ApiResponseMessage {

    private String status;
    private List<?> payload;
    private String errorCode;
    private String errorMsg;
    private String token;
}
