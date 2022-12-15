package com.findus.findus.api.report;

import com.findus.findus.model.report.ReportVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.logging.Handler;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportMapper reportMapper;

    public HashMap insertReport(ReportVO vo) {
        reportMapper.insertReport(vo);
        return null;
    }
}
