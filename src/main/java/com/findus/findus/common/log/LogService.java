package com.findus.findus.common.log;

import com.findus.findus.model.common.LogVO;
import com.findus.findus.model.work.WorkVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
@RequiredArgsConstructor
public class LogService {
    private final LogMapper logMapper;

    public void insertLog(LogVO vo) {
        logMapper.insertLog(vo);
    }
}
