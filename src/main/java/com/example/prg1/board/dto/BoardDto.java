package com.example.prg1.board.dto;

import com.example.prg1.board.Entity.Board;
import com.example.prg1.member.dto.MemberDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Board}
 */
//@Value
@Data
public class BoardDto implements Serializable {
    int id;
    String title;
    String content;
    MemberDto writer;
    LocalDateTime createdAt;
}