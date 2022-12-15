package com.findus.findus.api.board;

import com.findus.findus.model.board.BoardVO;
import com.findus.findus.model.board.CommentVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardSvc {
    private final BoardMapper boardMapper;

    //게시판 카테고리 조회
    public HashMap getBoardCategoryList(BoardVO vo) {
        HashMap map = new HashMap();
        map.put("list", boardMapper.getBoardCategoryList(vo));
        return map;
    }

    //게시판 조회
    public HashMap getBoardList(BoardVO vo) {
        HashMap map = new HashMap();
        if(vo.getCurPage() == 0) vo.setCurPage(1);
        vo.setPageUnit(12);
        vo.setFirstIndex( (vo.getCurPage() - 1) * vo.getPageUnit() );
        map.put("list", boardMapper.getBoardList(vo));
        map.put("isBoardEnd" , false);
        if( vo.getFirstIndex()+ vo.getPageUnit() >= boardMapper.getBoardListTotCnt(vo) ){
            map.put("isBoardEnd" , true);
        }
        return map;
    }

    //게시판 상세
    public HashMap getBoardDetail(BoardVO vo) {
        HashMap map = new HashMap();
        map.put("Board", boardMapper.getBoardDetail(vo));
        map.put("Comment", boardMapper.getBoardComment(vo));

        boardMapper.updateBoardViews(vo);
        return map;
    }

    //게시판 저장
    public HashMap saveBoard(BoardVO vo) {
        HashMap map = new HashMap();
        if(vo.getBoard_id() <= 0 ){
            boardMapper.insertBoard(vo);
            map.put("board_id" , vo.getBoard_id());
        }else{
            map.put("board_id" ,vo.getBoard_id());
            map.put("result" , boardMapper.updateBoard(vo));
        }
        return map;
    }
    //게시판 삭제
    public void deleteBoard(BoardVO vo) {
        boardMapper.deleteBoard(vo);
    }

    //댓글 저장
    public HashMap saveComment(CommentVo vo) {
        HashMap map = new HashMap();
        map.put("result" ,vo.getComment_id() <= 0 ? boardMapper.insertComment(vo) : boardMapper.updateComment(vo));
        return map;
    }

    //게시판 삭제
    public void deleteComment(CommentVo vo) {
        boardMapper.deleteComment(vo);
    }


    public void saveBoardLike(BoardVO vo) {
        if(boardMapper.getBoardLike(vo) > 0 ){
            boardMapper.deleteBoardLike(vo);
        }else{
            boardMapper.insertBoardLike(vo);
        }
        boardMapper.updateBoardLikeCnt(vo);
    }

    public void saveCommentLike(CommentVo vo) {
        if(boardMapper.getCommentLike(vo) > 0 ){
            boardMapper.deleteCommentLike(vo);
        }else{
            boardMapper.insertCommentLike(vo);
        }
        boardMapper.updateCommentLikeCnt(vo);
    }
}
