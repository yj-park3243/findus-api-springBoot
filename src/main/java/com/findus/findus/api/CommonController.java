package com.findus.findus.api;

import com.findus.findus.common.message.ApiResponseMessageMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping()
public class CommonController {
    @RequestMapping()
    public ResponseEntity<ApiResponseMessageMap>  homeRedirect() {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", null, "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
