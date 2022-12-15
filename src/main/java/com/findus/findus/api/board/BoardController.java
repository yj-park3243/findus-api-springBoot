package com.findus.findus.api.board;

import com.findus.findus.common.message.ApiResponseMessageMap;
import com.findus.findus.model.board.BoardVO;
import com.findus.findus.model.board.CommentVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@Tag(name = "3. 게시판", description = "게시판 CURD")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardSvc BoardSvc;

    @Operation(summary = "게시판 카테고리" )
    @GetMapping("/category")
    @SecurityRequirement(name = "Authentication")
    public ResponseEntity<ApiResponseMessageMap> category(BoardVO vo ) throws Exception {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", BoardSvc.getBoardCategoryList(vo), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @GetMapping("/list")
    @SecurityRequirement(name = "Authentication")
    @Operation(summary = "게시판 목록", description = "{ \"board_category_id\": \"1\"}")
    public ResponseEntity<ApiResponseMessageMap> list( @Schema(type = "object", example = "{\n" +
            "  \"curPage\": 1,\n" +
            "  \"board_category_id\": \"1\",\n" +
            "  \"search_txt\": \"\",\n" +
            "  \"is_best\": \"0\"\n" +
            "}") BoardVO vo) throws Exception {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", BoardSvc.getBoardList(vo), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Operation(summary = "게시판 상세")
    @SecurityRequirement(name = "Authentication")
    @GetMapping("/detail")
    public ResponseEntity<ApiResponseMessageMap> detail(@Schema(type = "object", example = "{\n" +
            "  \"board_id\": 80,\n" +
            "  \"board_category_id\": \"1\"\n" +
            "}") BoardVO vo) throws Exception {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", BoardSvc.getBoardDetail(vo), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Operation(summary = "게시판 작성")
    @SecurityRequirement(name = "Authentication")
    @PostMapping("/write")
    public ResponseEntity<ApiResponseMessageMap> insertBoard( @RequestBody BoardVO vo ) throws Exception {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", BoardSvc.saveBoard(vo), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Operation(summary = "게시판 삭제")
    @SecurityRequirement(name = "Authentication")
    @PostMapping("/board/delete")
    public ResponseEntity<ApiResponseMessageMap> deleteBoard( @RequestBody BoardVO vo ){
        BoardSvc.deleteBoard(vo);
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", null , "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    @Operation(summary = "댓글 작성")
    @SecurityRequirement(name = "Authentication")
    @PostMapping("/comment")
    public ResponseEntity<ApiResponseMessageMap> insertComment(@RequestBody CommentVo vo , HttpServletRequest request) throws Exception {
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", BoardSvc.saveComment(vo), "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Operation(summary = "댓글 삭제")
    @SecurityRequirement(name = "Authentication")
    @PostMapping("/comment/delete")
    public ResponseEntity<ApiResponseMessageMap> deleteComment( @RequestBody CommentVo vo ){
        BoardSvc.deleteComment(vo);
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", null , "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @Operation(summary = "게시판 좋아요")
    @SecurityRequirement(name = "Authentication")
    @PostMapping("/like")
    public ResponseEntity<ApiResponseMessageMap> like(@RequestBody BoardVO vo , HttpServletRequest request) throws Exception {
        BoardSvc.saveBoardLike(vo);
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", null, "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Operation(summary = "댓글 좋아요")
    @SecurityRequirement(name = "Authentication")
    @PostMapping("/comment/like")
    public ResponseEntity<ApiResponseMessageMap> commentLike(@RequestBody CommentVo vo , HttpServletRequest request) throws Exception {
        BoardSvc.saveCommentLike(vo);
        ApiResponseMessageMap message = new ApiResponseMessageMap("Success", null , "", "", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
