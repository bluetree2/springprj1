package com.example.prg1.board.serivce;

import com.example.prg1.board.Entity.Board;
import com.example.prg1.board.Repository.BoardRepository;
import com.example.prg1.board.dto.BoardDto;
import com.example.prg1.board.dto.BoardForm;
import com.example.prg1.board.dto.BoardListInfo;
import com.example.prg1.member.dto.MemberDto;
import com.example.prg1.member.entity.Member;
import com.example.prg1.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public void add(BoardForm formData, MemberDto user){
        Board board = new Board();
        board.setTitle(formData.getTitle());
        board.setContent(formData.getContent());
//        board.setWriter(user.getId());

        Member member = memberRepository.findById(user.getId()).get();
        board.setWriter(member);

        boardRepository.save(board);
    }

    public Map<String,Object> list(Integer page, String keyword) {
//        List<Board> list = boardRepository.findAll();

        Page<BoardListInfo> boardPage = null;

        if (keyword == null || keyword.isBlank()) {
            boardPage = boardRepository
                    .findAllBy(PageRequest.of(page -1, 10,
                            Sort.by("id").descending()));
        }else{
            boardPage = boardRepository
            .searchByKeyword("%"+keyword+"%",
                    PageRequest.of(page - 1,10,
                            Sort.by("id").descending()));
        }
        List<BoardListInfo> boardList = boardPage.getContent();

        Integer rightPageNumber = ((page - 1) / 10 + 1) * 10;
        Integer leftPageNumber = rightPageNumber - 9;
        rightPageNumber = Math.min(rightPageNumber, boardPage.getTotalPages());

        var result = Map.of("boardList", boardList,
                "totalElements", boardPage.getTotalElements(),
                "totalPages",boardPage.getTotalPages(),
                "rightPageNumber", rightPageNumber,
                "leftPageNumber", leftPageNumber,
                "currentPage",page
        );


        return result ;
    }


    public BoardDto  get(Integer id) {
        Board board = boardRepository.findById(id).get();
        BoardDto dto = new BoardDto();
        dto.setId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());

        MemberDto memberDto = new MemberDto();
        memberDto.setId(board.getWriter().getId());
        memberDto.setNickName(board.getWriter().getNickName());

        dto.setWriter(memberDto);
//        dto.setWriter(board.getWriter());
        dto.setCreatedAt(board.getCreatedAt());

        return dto;
    }

    public boolean remove(Integer id, MemberDto user) {
        if (user != null) {
            Member db = boardRepository.findById(id).get().getWriter();

            if (db.getId().equals(user.getId())) {
                boardRepository.deleteById(id);
                return true;
            }

        }

        return false;
    }

    public boolean update(BoardForm data, MemberDto user) {

        if (user != null){

            // 조회
            Board board = boardRepository.findById(data.getId()).get();
            if (board.getWriter().getId().equals(user.getId())) {
    //            System.out.println("11111111111111111111111111111111111");
                // 수정
                board.setTitle(data.getTitle());
                board.setContent(data.getContent());
                // 저장
                boardRepository.save(board);
                return true;
            }

        }
        return false;
    }
}
