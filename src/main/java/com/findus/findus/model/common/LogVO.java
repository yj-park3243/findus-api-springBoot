package com.findus.findus.model.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LogVO {
    private String auth_token;
    private String parameter;
    private String url;
    private String ip;
}
