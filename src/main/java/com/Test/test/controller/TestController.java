package com.Test.test.controller;

import com.Test.test.entity.Board;
import com.Test.test.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
public class TestController {

    @Autowired
    private final BoardService boardService;

    public TestController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/test/write")
    public String boardWriteForm(){


        return "boardwrite";
    }

    @PostMapping("/test/write1")
    public String boardWriteForm(Board board , Model model, @RequestParam(name="file", required = false) MultipartFile file) throws IOException {


        boardService.write(board, file);

        model.addAttribute("message" , "글 작성이 완료되었습니다");
        model.addAttribute("searchUrl", "/test/list");

        return "message";
    }


    // 글을 쓰고나서 글쓰기 목록 페이지로 이동할때 model 처리
    @GetMapping("/test/list")
    public String boardList(Model model){

        model.addAttribute("list", boardService.boardList());

        return "boardlist";
    }


    // 써져있는 글을 선택시 글의 상세내용 볼수있게 처리
    @GetMapping("/test/view") 
    //localhost:8080/test/view?id=1 이라고 하면 id의 1인 게시글의 정보가 넘어옴
    public String boardView(Model model, @RequestParam(name = "id") Integer id){

        model.addAttribute("board" , boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/test/delete")
    public String boardDelete(@RequestParam(name = "id") Integer id){

        boardService.boardDelete(id);

        return "redirect:/test/list";
    }


    @GetMapping("/test/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){
        // PathVariable은 ("id")를 넘겨받아서 Integer형태의 id로 인식을 한다.


    model.addAttribute("board" , boardService.boardView(id));

        return "boardmodify";
    }


    @PostMapping("/test/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, @RequestParam(name="file", required = false) MultipartFile file) throws IOException {
        // PathVariable은 ("id")를 넘겨받아서 Integer형태의 id로 인식을 한다.

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);

        return "redirect:/test/list";
    }




}
