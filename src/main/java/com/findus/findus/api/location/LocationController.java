package com.findus.findus.api.location;

import com.findus.findus.common.message.ApiResponseMessageMap;
import com.findus.findus.model.location.LocationVO;
import com.findus.findus.model.user.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
@Tag(name = "2. 지도", description = "지도 마크")
public class LocationController {
    private final LocationSvc locaionSvc;

    @GetMapping("/category")
    @Operation(summary = "지도 카테고리")
    @SecurityRequirement(name = "Authentication")
    public ResponseEntity<ApiResponseMessageMap> getCategory(UserVO vo) {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", locaionSvc.getLocationCategory(vo), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/list")
    @Operation(summary = "지도 목록")
    @SecurityRequirement(name = "Authentication")
    public ResponseEntity<ApiResponseMessageMap> getList(@Schema(type = "object", example = "{\n" +
            "  \"user_lat\": \"37.49398465726706\",\n" +
            "  \"user_lng\": \"127.12252495566865\",\n" +
            "  \"language\": \"en\"\n" +
            "}") UserVO vo) throws Exception {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", locaionSvc.getLocationList(vo), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/detail")
    @Operation(summary = "지도 상세")
    @SecurityRequirement(name = "Authentication")
    public ResponseEntity<ApiResponseMessageMap> getDetail(@Schema(type = "object", example = "{\n" +
            "  \"location_id\": 1 , \n" +
            "  \"language\": \"en\"\n" +
            "}") LocationVO vo) throws Exception {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", locaionSvc.getDetail(vo), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/getAddress")
    @Operation(summary = "주소 가져오기")
    @SecurityRequirement(name = "Authentication")
    public ResponseEntity<ApiResponseMessageMap> getAddress(@Schema(type = "object", example = "{\n" +
            "  \"latitude\": \"37.49398465726706\" , \n" +
            "  \"longitude\": \"127.12252495566865\"\n" +
            "}") LocationVO vo) throws Exception {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", locaionSvc.getAddress(vo), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    @GetMapping("/temp")
    @Operation(summary = "주소 가져오기")
    @SecurityRequirement(name = "Authentication")
    public ResponseEntity<ApiResponseMessageMap> temp() throws Exception {
        locaionSvc.tempAddr();
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", null , "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
