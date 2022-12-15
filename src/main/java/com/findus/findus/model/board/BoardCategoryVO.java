package com.findus.findus.model.board;

import com.findus.findus.model.common.CommonDefaultVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BoardCategoryVO extends CommonDefaultVO {

    private int board_id;
    private String board_category_id;
    private String category_name_nm;
    private String category_name_en;
    private int sort;
    //private int PARENT_CATEGORY_ID;
}
