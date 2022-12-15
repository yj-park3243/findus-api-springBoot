package com.findus.findus.api.work;

import com.findus.findus.common.message.ApiResponseMessageMap;
import com.findus.findus.model.work.WorkApplyVO;
import com.findus.findus.model.work.WorkVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "4. 잡 게시판", description = "일자리 CURD")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/work")
public class WorkController {
    private final WorkSvc WorkSvc;

    @Operation(summary = "일자리 게시판 카테고리" )
    @GetMapping("/category")
    @SecurityRequirement(name = "Authentication")
    public ResponseEntity<ApiResponseMessageMap> category(@Schema(type = "object" , example = "{\n" +
            "  \"language\": \"en\"\n" +
            "}") WorkVO vo ) {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", WorkSvc.getWorkCategoryList(vo), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @GetMapping("/list")
    @SecurityRequirement(name = "Authentication")
    @Operation(summary = "일자리 게시판 목록", description = "{ \"board_category_id\": \"1\"}")
    public ResponseEntity<ApiResponseMessageMap> list(@Schema(type = "object" , example = "{\n" +
            "  \"curPage\": 1,\n" +
            "  \"language\": \"en\"\n" +
            "}") WorkVO vo) throws Exception {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", WorkSvc.getWorkList(vo), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    @Operation(summary = "일자리 게시판 상세")
    @SecurityRequirement(name = "Authentication")
    @GetMapping("/detail")
    public ResponseEntity<ApiResponseMessageMap> detail( WorkVO vo) {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", WorkSvc.getWorkDetail(vo), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Operation(summary = "일자리 게시판 작성")
    @SecurityRequirement(name = "Authentication")
    @PostMapping("/write")
    public ResponseEntity<ApiResponseMessageMap> insertBoard( @RequestBody WorkVO vo ) throws IOException, ParseException {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", WorkSvc.saveWork(vo), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Operation(summary = "일자리 삭제")
    @SecurityRequirement(name = "Authentication")
    @PostMapping("/delete")
    public ResponseEntity<ApiResponseMessageMap> deleteWork( @RequestBody WorkVO vo ) throws Exception {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", WorkSvc.deleteWork(vo), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
//
//    @Operation(summary = "일자리 지원 게시판  목록")
//    @SecurityRequirement(name = "Authentication")
//    @GetMapping("/apply")
//    public ResponseEntity<ApiResponseMessageMap> applyList(@RequestBody WorkApplyVO vo ) throws Exception {
//        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", WorkSvc.applyList(vo), "", "", null);
//        return new ResponseEntity<>(message, HttpStatus.OK);
//    }
//
//
//
//    @Operation(summary = "일자리 지원 여부 관리")
//    @SecurityRequirement(name = "Authentication")
//    @PostMapping("/apply")
//    public ResponseEntity<ApiResponseMessageMap> insertBoard( @RequestBody WorkApplyVO vo ) throws Exception {
//        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", WorkSvc.saveWorkApply(vo), "", "", null);
//        return new ResponseEntity<>(message, HttpStatus.OK);
//    }


}
