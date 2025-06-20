package com.example.prg1.serivce;

import com.example.prg1.Entity.Board;
import com.example.prg1.Repository.BoardRepository;
import com.example.prg1.dto.BoardForm;
import com.example.prg1.dto.BoardListInfo;
import lombok.RequiredArgsConstructor;
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
                .findAllBy(PageRequest.of(page - 1,10, Sort.by("id").descending() ));

        return boardList ;
//        return list;
//        return boardRepository.findAll();
    }
}
