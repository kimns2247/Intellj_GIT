package com.Test.test.service;

import com.Test.test.entity.Board;
import com.Test.test.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    //글 작성
    public void write(Board board, MultipartFile file) throws IOException {

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
        // 프로젝의 경로를 projectPath라는 변수에 담아주게 된다.

        UUID uuid = UUID.randomUUID(); //랜덤으로 식별자를 생성해준다.

        String fileName = uuid + "_" + file.getOriginalFilename();
        // 이러면 랜덤하게 만들어진 식별자에 언더바를 붙여서 파일의 원래 이름이 붙어서 이름이 새롭게 만들어진다.

        File saveFile = new File(projectPath + "\\" + file.getOriginalFilename());

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public List<Board> boardList() {

        return boardRepository.findAll();
    }


    // 특정 게시글 불러오기
    public Board boardView(Integer id) {

        return boardRepository.findById(id).get();
    }


    // 선택 게시글 삭제하기
    public void boardDelete(Integer id) {

        boardRepository.deleteById(id);
    }
}
