package com.example.prg1.serivce;

import com.example.prg1.Entity.Board;
import com.example.prg1.Repository.BoardRepository;
import com.example.prg1.dto.BoardForm;
import com.example.prg1.dto.BoardListInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void add(BoardForm formData){
        Board board = new Board();
        board.setTitle(formData.getTitle());
        board.setContent(formData.getContent());
        board.setWriter(formData.getWriter());

        boardRepository.save(board);
    }

    public List<BoardListInfo> list(Integer page) {
//        List<Board> list = boardRepository.findAll();

        List<BoardListInfo> boardList = boardRepository
                .findAllBy(PageRequest.of(page - 1,5, Sort.by("id").descending() ));



        return boardList ;
//        return list;
//        return boardRepository.findAll();
    }

    public Object countlist() {


        Page<Board> boardList = boardRepository
                .findAll(PageRequest.of(1 - 1,5, Sort.by("id")));
        Integer totalPages = boardList.getTotalPages();
        List<Integer> result = new ArrayList<>();
        for (int i=0;i < totalPages;i++) {
            result.add(i+1);
        }

        System.out.println("totalPages = " + totalPages);
        System.out.println("result = " + result);
//        Integer count = boardRepository.countBy();

        return result;
    }
}
