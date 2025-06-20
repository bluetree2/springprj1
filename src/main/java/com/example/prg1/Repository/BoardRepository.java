package com.example.prg1.Repository;

import com.example.prg1.Entity.Board;
import com.example.prg1.dto.BoardListInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<BoardListInfo> findAllBy();

    List<BoardListInfo> findAllBy(Pageable pageable);

    Integer countBy();
}