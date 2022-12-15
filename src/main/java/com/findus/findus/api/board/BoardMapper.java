package com.findus.findus.api.board;

import com.findus.findus.model.board.BoardVO;
import com.findus.findus.model.board.CommentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface BoardMapper {
    List<HashMap>getBoardCategoryList(BoardVO vo);//카테고리 조회
    List<HashMap> getBoardList(BoardVO vo);//게시판 조회
    int getBoardListTotCnt(BoardVO vo); //게시판 갯수
    HashMap getBoardDetail(BoardVO vo); //게시판 상세
    List<HashMap> getBoardComment(BoardVO vo);//게시판 댓글
    int insertBoard(BoardVO vo); //게시글 등록
    int updateBoard(BoardVO vo); //게시판 업데이트
    int deleteBoard(BoardVO vo); //게시판 업데이트
    int insertComment(CommentVo vo); //댓글 등록
    int updateComment(CommentVo vo); //댓글 업데이트
    int deleteComment(CommentVo vo); //댓글 업데이트

    void updateBoardViews(BoardVO vo); //조회수 올리기


    int getBoardLike(BoardVO vo);
    int insertBoardLike(BoardVO vo);
    int deleteBoardLike(BoardVO vo);
    int updateBoardLikeCnt(BoardVO vo);

    int getCommentLike(CommentVo vo);
    int insertCommentLike(CommentVo vo);
    int deleteCommentLike(CommentVo vo);
    int updateCommentLikeCnt(CommentVo vo);
}
