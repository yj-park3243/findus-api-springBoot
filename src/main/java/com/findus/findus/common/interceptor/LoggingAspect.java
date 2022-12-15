package com.findus.findus.common.interceptor;

import com.findus.findus.common.jwt.JwtTokenProvider;
import com.findus.findus.common.log.LogService;
import com.findus.findus.model.common.LogVO;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JwtTokenProvider jwtTokenProvider;
    private final LogService logService;


    @Around("execution(* com.findus.findus..*Controller.*()) || execution(* com.findus.findus..*Controller.*(..))")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } finally {
            // 로그를 기록할 매소드 실행 후 로그기록 시작
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();


            String body;
            body = params(joinPoint);

            logger.info("호출한 params : {}",body);
            logger.info( "========================request  끝 =======================================request  끝 =====================request  끝 ====================request  끝 =========");
            logger.info(" ");

            if(!request.getRequestURI().contains("login") || !request.getRequestURI().contains("version")) {
                LogVO vo = new LogVO();
                try {
                    vo.setIp(new LoggerInterceptor(jwtTokenProvider).getClientIP(request));
                    vo.setAuth_token(jwtTokenProvider.getAuthToken(jwtTokenProvider.resolveToken(request)));
                    vo.setParameter(body.toString());
                    vo.setUrl(request.getRequestURI());
                    logService.insertLog(vo);
                } catch (Exception e) {
                }
            }
        }
    }

    private String params(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        String returnStr = "";
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            String temp = args[i].toString();
            temp = temp.replace( "(" , " : ");
            temp = temp.replace( ")" , "");
            try{
                returnStr += temp.split(":")[0]+ " : ";
                String [] tempArr =  temp.split(":")[1].split(",");
                for(String a : tempArr){
                    if(a.contains("=null")) continue;
                    if(a.length()>100) a = a.substring(0,100)+"......";
                    returnStr += a + ',';
                }
                returnStr = returnStr.substring(0, returnStr.lastIndexOf(","));
            }catch (Exception e){}
            //params.put(parameterNames[i], args[i]);
        }
        return returnStr;
    }

}
