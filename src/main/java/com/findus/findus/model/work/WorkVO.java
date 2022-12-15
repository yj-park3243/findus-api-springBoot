package com.findus.findus.model.work;

import com.findus.findus.model.common.CommonDefaultVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class WorkVO extends CommonDefaultVO {
    private int work_region_id;
    private int work_category_id;
    private String auth_token;
    private int before_work;
    private int work_id;

    private String subject;
    private String content;

    private String work_addr1_en;
    private String work_addr2_en;
    private String work_addr1_ko;
    private String work_addr2_ko;

    private String work_lat;
    private String work_lng;
    private String work_phone;
    private int work_pay;
    private Date end_date;
    private int my_work;

}
