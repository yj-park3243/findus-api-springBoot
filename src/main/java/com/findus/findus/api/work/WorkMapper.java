package com.findus.findus.api.work;

import com.findus.findus.model.work.WorkApplyVO;
import com.findus.findus.model.work.WorkVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface WorkMapper {
    List<HashMap> getWorkCategoryList(WorkVO vo); //카테고리 조회
    List<HashMap> getWorkRegionList(WorkVO vo); //지역 조회

    List<HashMap> getWorkList(WorkVO vo); //카테고리 조회

//    Object getWorkComment(WorkVO vo); //카테고리 조회

    int getWorkListTotCnt(WorkVO vo); //게시판 갯수

    HashMap getWorkDetail(WorkVO vo); //게시판 상세

    int insertWork(WorkVO vo); //게시글 등록

    int updateWork(WorkVO vo); //게시판 업데이트

    int deleteWork(WorkVO vo); //게시판 업데이트

    int updateWorkViews(WorkVO vo); //게시판 업데이트

    /*List<HashMap> getApplyList(WorkApplyVO vo); // 일자리 지원 목록

    int insertApply(WorkApplyVO vo); // 일자리 지원 등록

    int deleteApply(WorkApplyVO vo); // 일자리 지원 취소*/
}
