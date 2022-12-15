package com.findus.findus.api.location;

import com.findus.findus.model.location.LocationVO;
import com.findus.findus.model.user.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface LocationMapper {

    //공지사항 조회
    List<HashMap> getLocationList(UserVO vo);
    int getLocationListTotCnt(UserVO vo);
    List<HashMap> getLocationCategory(UserVO vo);
    List<HashMap> getLocationImages(LocationVO vo);
    List<HashMap> getLocationOpenTime(LocationVO vo);

    int tempLocationAddr(HashMap map);
}
