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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;


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
    public String writePost(BoardForm data, RedirectAttributes rttr) {

        boardService.add(data);

        rttr.addFlashAttribute("alert", Map.of("code","primary","message","새 게시물이 등록되었습니다"));

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

    @PostMapping("remove")
    public String remove(Integer id, RedirectAttributes rttr) {
        boardService.remove(id);

        rttr.addFlashAttribute("alert",
                Map.of("code",
                        "danger","message",id+"번 게시물이 삭제 되었습니다"));

        return "redirect:/board/list";
    }

    @GetMapping("edit")
    public String edit(Integer id, Model model) {
        var dto = boardService.get(id);
        model.addAttribute("board",dto);
        return "board/edit";
    }

    @PostMapping("edit")
    public String editPost(BoardForm data, RedirectAttributes rttr) {
        boardService.update(data);

        rttr.addFlashAttribute("alert",
                Map.of("code", "success","message",
                        data.getId() + "번 게시물이 수정되었습니다"));

        rttr.addAttribute("id", data.getId());

        return "redirect:/board/list";
    }
}
