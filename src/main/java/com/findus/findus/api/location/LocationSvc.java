package com.findus.findus.api.location;

import com.findus.findus.common.util.GeoPointUtil;
import com.findus.findus.model.location.LocationVO;
import com.findus.findus.model.user.UserVO;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationSvc {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LocationMapper locationMapper;
    public HashMap getLocationList(UserVO vo) {
        if(vo.getLanguage() ==null || "".equals(vo.getLanguage())) vo.setLanguage("en");
        HashMap map = new HashMap();
        //vo.setFirstIndex((vo.getCurPage() - 1) * vo.getPageUnit());

        List<HashMap> list = locationMapper.getLocationList(vo);
        for(int i=0; i<list.size(); i++){
            String Sdistance = "";
            try{
                double distance = (double) list.get(i).get("distance");
                if(distance == 0){
                    continue;
                }else if(distance > 100){
                    Sdistance = String.format(String.valueOf(Math.round(distance)))+"Km";
                }else if(distance > 1){
                    Sdistance = String.format("%.1f",distance)+"Km";
                }else{
                    Sdistance = String.format("%.0f",distance*1000)+"m";
                }
                Sdistance = Sdistance.replace(".0","");
            }catch (Exception e){}
            list.get(i).put("distance" , Sdistance);
        }
        map.put("list", list);
        //map.put("totalCnt", locationMapper.getLocationListTotCnt(vo));

        return map;
    }
    public HashMap getLocationCategory(UserVO vo){
        HashMap map = new HashMap();
        if(vo.getLanguage() ==null || "".equals(vo.getLanguage())) vo.setLanguage("en");
        map.put("list", locationMapper.getLocationCategory(vo));
        return map;

    }

    public HashMap getDetail(LocationVO vo){
        HashMap map = new HashMap();
        if(vo.getLanguage() ==null || "".equals(vo.getLanguage())) vo.setLanguage("en");
        map.put("images",locationMapper.getLocationImages(vo) );
        map.put("open_time",locationMapper.getLocationOpenTime(vo) );
        return map;

    }

    public HashMap getAddress(LocationVO vo){
        HashMap map = new HashMap();
        String addr ;
        try {
             addr = GeoPointUtil.findGeoAddr(vo, vo.getLanguage());
             map.put("addr" , addr);
            logger.info(addr);
        } catch (Exception e) {
        }
        return map;
    }


    public void tempAddr() throws IOException, ParseException {
        UserVO user = new UserVO();
        user.setLanguage("ko");
        List<HashMap> list = locationMapper.getLocationList(user);
        for(HashMap map : list){
            LocationVO vo = new LocationVO();
            vo.setLatitude( map.get("latitude").toString());
            vo.setLongitude( map.get("longitude").toString());
            map.put("address1_en" , GeoPointUtil.findGeoAddr(vo, "en"));
            map.put("address1_ko" , GeoPointUtil.findGeoAddr(vo, "ko"));
            locationMapper.tempLocationAddr(map);
        }
    }
}
