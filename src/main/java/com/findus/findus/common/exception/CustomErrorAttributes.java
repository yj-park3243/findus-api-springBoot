package com.findus.findus.common.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map superMap = super.getErrorAttributes(webRequest, options);
        Map resMap = new HashMap();

        resMap.put("status","ERROR");
        resMap.put("errorCode",superMap.get("status"));
        resMap.put("errorMsg",superMap.get("error"));

        return resMap;
    }
}
