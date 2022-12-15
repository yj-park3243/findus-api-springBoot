package com.findus.findus.model.board;

import com.findus.findus.model.common.CommonDefaultVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommentVo extends CommonDefaultVO {

    private int comment_id;
    private int board_id;
    private String parent_comment;
    private String comment;
    private String auth_token;
}
