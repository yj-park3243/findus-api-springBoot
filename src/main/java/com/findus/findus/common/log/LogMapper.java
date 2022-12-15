package com.findus.findus.common.log;

import com.findus.findus.model.common.LogVO;
import com.findus.findus.model.work.WorkVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

    int insertLog(LogVO vo);
}
