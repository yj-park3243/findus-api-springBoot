package com.findus.findus.common.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class ApiResponseMessageMap {

    private String status;
    private HashMap payload;
    private String errorCode;
    private String errorMsg;
    private String token;

}
