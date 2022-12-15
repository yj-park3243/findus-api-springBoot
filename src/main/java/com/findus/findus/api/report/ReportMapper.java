package com.findus.findus.api.report;

import com.findus.findus.model.report.ReportVO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ReportMapper {
    int insertReport(ReportVO vo);
}
