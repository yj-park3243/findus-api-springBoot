package com.findus.findus.api.user;

import com.findus.findus.common.message.ApiResponseMessageMap;
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
@Tag(name = "1. 앱 로그인", description = "로그인")
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseEntity<ApiResponseMessageMap> login(@Schema(type = "object", example = "{\n" +
            "  \"auth_token\": \"auth_token\"\n" +
            "}") @RequestBody UserVO uservo, HttpServletRequest request) throws Exception {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", userService.login(uservo, request), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @PostMapping("/version")
    @Operation(summary = "버전")
    public ResponseEntity<ApiResponseMessageMap> version() throws Exception {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", userService.getVersion(), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/user")
    @Operation(summary = "회원 정보 수정(닉네임), 이메일")
    @SecurityRequirement(name = "Authentication")
    public ResponseEntity<ApiResponseMessageMap> userUpdate(@RequestBody UserVO vo){
        userService.userUpdate(vo);
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", null, "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/user/profile")
    @Operation(summary = "프로필 사진 수정")
    @SecurityRequirement(name = "Authentication")
    public ResponseEntity<ApiResponseMessageMap> uploadProfile( UserVO vo){
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", userService.uploadProfile( vo), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    @PostMapping("/user/nickName/{nickname}")
    @Operation(summary = "닉네임 존재 여부")
    public ResponseEntity<ApiResponseMessageMap> isExistNickName( @PathVariable("nickname") String nickname){
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", userService.isExistNickName( nickname ), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/user/ban")
    @Operation(summary = "유저 벤")
    @SecurityRequirement(name = "Authentication")
    public ResponseEntity<ApiResponseMessageMap> updateBanUser(@RequestBody UserVO vo, HttpServletRequest request ){
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", userService.insertBanUser( vo.getVanUserToken(),  request ), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/user/ban/delete")
    @Operation(summary = "유저 벤")
    @SecurityRequirement(name = "Authentication")
    public ResponseEntity<ApiResponseMessageMap> deleteBanUser( @RequestBody UserVO vo, HttpServletRequest request ){
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", userService.deleteBanUser( vo.getBan_id(),  request ), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/user/ban")
    @Operation(summary = "차단된 유저목록 가져오기")
    @SecurityRequirement(name = "Authentication")
    public ResponseEntity<ApiResponseMessageMap> getBanUser( HttpServletRequest request ){
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", userService.getBanUserList( request ), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
