package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RestBoardApiController {

    @Autowired
    private BoardService boardService;

    //게시글 조회
    @GetMapping("/api/boards")
    public Page<Board> boardList(Pageable pageable){
        return boardService.boardList(pageable);
    }
    // 게시글 작성
    @PostMapping("/api/boards")
    public void write(@RequestBody Board board, @RequestParam(value ="file", required = false) MultipartFile file ) throws Exception {
        boardService.write(board, file);
    }
    //게시글 상세조회
    @GetMapping("/api/boards/{id}")
    public Board boardView(@RequestParam("id") Integer id) throws Exception{
        return boardService.boardView(id);
    }

    //게시글 수정
    @PutMapping ("/api/boards/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, @RequestBody Board board, @RequestParam(value ="file", required = false) MultipartFile file) throws Exception{

        // 기존 정보 가져오고 수정된 부분을 덮어씌우기
        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);

        return "redirect: /board/list";
    }


    //게시글 삭제
    @DeleteMapping ("/api/board/{id}")
    public String boardDelete(@RequestParam("id") Integer id) {
        boardService.boardDelete(id);

        return "redirect: /board/list";
    }



}
