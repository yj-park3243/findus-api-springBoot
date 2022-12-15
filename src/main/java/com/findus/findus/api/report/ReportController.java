package com.findus.findus.api.report;

import com.findus.findus.api.user.UserService;
import com.findus.findus.common.message.ApiResponseMessageMap;
import com.findus.findus.model.report.ReportVO;
import com.findus.findus.model.user.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Tag(name = "5. 문의/신고", description = "문의/신고")
@RequestMapping("/api")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/report")
    @Operation(summary = "문의")
    @SecurityRequirement(name = "Authentication")
    public ResponseEntity<ApiResponseMessageMap> uploadProfile(@RequestBody ReportVO vo){
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", reportService.insertReport( vo ), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
