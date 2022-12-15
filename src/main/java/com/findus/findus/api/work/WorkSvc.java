package com.findus.findus.api.work;

import com.findus.findus.common.util.GeoPointUtil;
import com.findus.findus.model.location.LocationVO;
import com.findus.findus.model.work.WorkApplyVO;
import com.findus.findus.model.work.WorkVO;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkSvc {
    private final WorkMapper workMapper;
    public HashMap getWorkCategoryList(WorkVO vo) {
        if(vo.getLanguage() =="ko" || "".equals(vo.getLanguage())) vo.setLanguage("en");
        HashMap map = new HashMap();
        map.put("category", workMapper.getWorkCategoryList(vo));
        map.put("region", workMapper.getWorkRegionList(vo));
        return map;
    }

    public HashMap getWorkList(WorkVO vo) {
        if(vo.getLanguage() =="ko" || "".equals(vo.getLanguage())) vo.setLanguage("en");
        HashMap map = new HashMap();
        if(vo.getCurPage() == 0) vo.setCurPage(1);
        vo.setPageUnit(12);
        vo.setFirstIndex( (vo.getCurPage() - 1) * vo.getPageUnit() );
        map.put("list", workMapper.getWorkList(vo));
        map.put("isEnd" , false);
        if( vo.getFirstIndex()+ vo.getPageUnit() >= workMapper.getWorkListTotCnt(vo) ){
            map.put("isEnd" , true);
        }
        return map;
    }

    public HashMap getWorkDetail(WorkVO vo) {
        if(vo.getLanguage() =="ko" || "".equals(vo.getLanguage())) vo.setLanguage("en");
        HashMap map = workMapper.getWorkDetail(vo);
        workMapper.updateWorkViews(vo);
        return map;
    }

    public HashMap saveWork(WorkVO vo) throws IOException, ParseException {
        HashMap map = new HashMap();

        LocationVO locationVO = new LocationVO();
        locationVO.setLatitude( vo.getWork_lat());
        locationVO.setLongitude( vo.getWork_lng());

        vo.setWork_addr1_en( GeoPointUtil.findGeoAddr(locationVO, "en").replace("South Korea," ,"").replace(", South Korea" ,"") );
        vo.setWork_addr2_en( vo.getWork_addr2_ko());

        if(vo.getWork_id() <= 0){
            workMapper.insertWork(vo);
            map.put("work_id" , vo.getWork_id());
        }else{
            map.put("work_id" ,vo.getWork_id());
            map.put("result" , workMapper.updateWork(vo));
        }
        return map;
    }

    public HashMap deleteWork(WorkVO vo) {
        workMapper.deleteWork(vo);
        return null;
    }
/*
    public HashMap applyList(WorkApplyVO vo) {

        HashMap map = new HashMap();
        map.put("list", workMapper.getApplyList(vo));
        return map;
    }

    public HashMap saveWorkApply(WorkApplyVO vo) {

        HashMap map = new HashMap();
        if(vo.getApply_id() == -1){
            map.put("apply_id" , workMapper.insertApply(vo));
        }else{
            map.put("apply_id" ,vo.getBoard_id());
            map.put("result" , workMapper.deleteApply(vo));
        }

        return map;
    }*/
}
