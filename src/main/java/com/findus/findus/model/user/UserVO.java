package com.findus.findus.model.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserVO {

    private String auth_token;
    private String user_name;
    private String user_email;
    private String user_nickname;
    private String profile_url;
    @Schema(description = "회원 위치 위도" , defaultValue = "")
    private String user_lat;
    @Schema(description = "회원 위치 경도" , defaultValue = "")
    private String user_lng;
    private String language;
    private String ip;
    private MultipartFile file;
    private int fileID;
    private int email_verification;
    private String platform;
    private String vanUserToken;
    private String ban_id;
}
