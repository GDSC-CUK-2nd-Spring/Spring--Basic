package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    //글 작성 처리
    public void write(Board board, MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            // 파일이 없을 때 수행할 기본 동작
            System.out.println("파일이 없거나 비어 있습니다. 기본 동작을 수행합니다.");
        } else {
            // 파일이 있을 때의 처리 로직
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
            UUID uuid = UUID.randomUUID(); //랜덤 식별자

            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, "fileName");

            file.transferTo(saveFile);

            board.setFilename(fileName);
            board.setFilepath("/files/" + fileName);
            System.out.println("업로드된 파일: " + fileName);
        }

        boardRepository.save(board);

    }
    //게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){

        return boardRepository.findAll(pageable);  //board 클래스가 담긴 리스트 반환
    }

    // 검색 기능 처리
    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable){

        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }
    //특정게시물 불러오기
    public Board boardView(Integer id){

        return boardRepository.findById(id).get(); //id 받기
    }

    //특정 게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }
}
