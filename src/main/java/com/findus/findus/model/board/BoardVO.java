package com.findus.findus.model.board;

import com.findus.findus.model.common.CommonDefaultVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BoardVO extends CommonDefaultVO {

    private int board_id;
    private String board_category_id;
    private String subject;
    private String content;
    private String notice_yn;
    private String auth_token;
    private String views;
    private String user_nickname;


    @Schema(description = "회원 위치 위도" )
    private String user_lat;
    @Schema(description = "회원 위치 경도" )
    private String user_lng;
    private String is_best;
}

