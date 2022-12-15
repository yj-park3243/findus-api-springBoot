package com.findus.findus.common.interceptor;

import com.findus.findus.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequiredArgsConstructor
@Component
public class LoggerInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try{
            if (request.getRequestURI().contains("swagger") || request.getRequestURI().contains("/v3/api-docs")) {
                logger.info(setPad("swagger 접속  IP : " + getClientIP(request), "="));
                return true;
            }
            logger.info( "========================request  시작====================request  시작=====");
            logger.info(setPad( "IP : " + getClientIP(request)  ,"=") );
            logger.info(setPad( "Method : " + request.getMethod() + "  " + request.getRequestURI() ,"=" ));
            String token = jwtTokenProvider.resolveToken(request);
            JSONObject object = jwtTokenProvider.tokenValue(token);
            logger.info(setPad( "nickname : " + object.get("user_nickname") + "    /  email : " + object.get("user_email") ,"=" ));
        }catch (Exception e){}
        return true;
    }
    // LPAD
    private static String setPad( String strContext , String strChar) {
        int iLen = 10;
        int maxLen = 50;
        StringBuilder sbAddChar = new StringBuilder();
        for( int i = 0; i < iLen; i++ ) {
            sbAddChar.append( strChar );
        };
        String result = sbAddChar + " "+ strContext + " ";

        sbAddChar = new StringBuilder();
        for( int i = result.length(); i < maxLen; i++ ) {
            sbAddChar.append( strChar );
        };
        return  result + sbAddChar;
    }

    public String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
