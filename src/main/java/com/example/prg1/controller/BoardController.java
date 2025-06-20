package com.example.prg1.controller;

import com.example.prg1.dto.BoardForm;
import com.example.prg1.serivce.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("write")
    public String write() {


        return "board/write";
    }

    /*@PostMapping("write")
    public String writePost(String title, String content,String writer) {
        System.out.println("title = " + title);
        System.out.println("content = " + content);
        System.out.println("writer = " + writer);

        return "board/write";
    }*/

    @PostMapping("write")
    public String writePost(BoardForm data) {

        boardService.add(data);

        return "board/write";
    }

    @GetMapping("list")
    public String list(
            @RequestParam(defaultValue = "1")
            Integer page,
            Model model) {

        var result = boardService.list(page);

//        model.addAttribute("boardList", result);
        model.addAllAttributes(result);
        return "board/list";
    }

    @GetMapping("view")
    public String view(Integer id, Model model) {
        // service
        var dto = boardService.get(id);

        // model
        model.addAttribute("board",dto);

        // view로 forward
        return "board/view";
    }
}
