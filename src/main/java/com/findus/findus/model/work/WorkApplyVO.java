package com.findus.findus.model.work;

import com.findus.findus.model.common.CommonDefaultVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class WorkApplyVO  extends CommonDefaultVO {

    private int apply_id;
    private String comment;
    private String auth_token;
    private String user_nickname;
    private int board_id;
    private String board_category_id;
}
