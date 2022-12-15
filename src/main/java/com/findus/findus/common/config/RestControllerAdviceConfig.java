package com.findus.findus.common.config;

import com.findus.findus.api.user.UserMapper;
import com.findus.findus.common.message.ApiResponseMessage;
import com.findus.findus.common.message.ApiResponseMessageMap;
import com.findus.findus.common.jwt.JwtTokenProvider;
import com.findus.findus.model.user.UserVO;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Date;
import java.util.List;


@RestControllerAdvice
@RequiredArgsConstructor
@Transactional
public class RestControllerAdviceConfig implements ResponseBodyAdvice {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RestControllerAdviceConfig() {
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        ApiResponseMessage message = new ApiResponseMessage(null,null,null,null,null);
        ApiResponseMessageMap messageMap = new ApiResponseMessageMap(null,null,null,null,null);
        try{
            if (body.getClass().getName().contains("ApiResponseMessageMap")){
                messageMap = (ApiResponseMessageMap)body;
            } else if(body.getClass().getName().contains("ApiResponseMessage")){
                message = (ApiResponseMessage)body;
            }else{
                return body;
            }
        }catch (Exception e){
            return null;
        }

        List<String> token = jwtTokenProvider.resolveToken(request);

        if (token == null){
            if (body.getClass().getName().contains("ApiResponseMessageMap")){
                return messageMap;
            } else {
                return message;
            }
        }

        if (token.get(0) == null || "".equals(token.get(0))) {
            if (body.getClass().getName().contains("ApiResponseMessageMap")){
                return messageMap;
            } else {
                return message;
            }
        }


        try {
            if( /* 종료일자 - 리프레시 시간 < 현재*/ jwtTokenProvider.getRefreshDate( jwtTokenProvider.getTokenExpiration( token.get(0))) .after(new Date()  )){
                if (body.getClass().getName().contains("ApiResponseMessageMap")){
                    return messageMap;
                } else {
                    return message;
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String auth_token = null;

        auth_token = jwtTokenProvider.getAuthToken(token.get(0));

        UserVO userVO = new UserVO();
        userVO.setAuth_token(auth_token);
        String jwt = jwtTokenProvider.createToken(userMapper.getUserInfo(userVO));

        if (body.getClass().getName().contains("ApiResponseMessageMap")){
            messageMap.setToken(jwt);
            return messageMap;
        } else {
            message.setToken(jwt);
            return message;
        }
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        //logger.info("returnType : {}",returnType.getMethod().getName());

        if ("error".equals(returnType.getMethod().getName())
                || returnType.getMethod().getName().contains("Exception")
                || "login".equals(returnType.getMethod().getName())
                || "menu".equals(returnType.getMethod().getName())
                || "resetPwd".equals(returnType.getMethod().getName())
        )
            return false;
        else
            return true;
    }
}
