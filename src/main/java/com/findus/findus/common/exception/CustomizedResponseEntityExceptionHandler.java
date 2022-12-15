package com.findus.findus.common.exception;

import com.findus.findus.common.message.ApiResponseMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<Object> handleUnauthorizedException(Exception ex) {
        ex.printStackTrace();
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage("ERROR",null, "401", ex.getMessage(), null);
        return new ResponseEntity(apiResponseMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundExceptions(Exception ex) {
        ex.printStackTrace();
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage("ERROR",null, "404", ex.getMessage(), null);
        return new ResponseEntity(apiResponseMessage, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage("ERROR",null, status.toString(), ex.getMessage(), null);
        return new ResponseEntity(apiResponseMessage, status);
    }
}
