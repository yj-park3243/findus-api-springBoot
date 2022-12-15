package com.findus.findus.model.report;

import com.findus.findus.model.common.CommonDefaultVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReportVO  {

    private int report_id;
    private int report_type;
    private int report_type_id;
    private String report_title;
    private String report_content;
    private String auth_token;
}
