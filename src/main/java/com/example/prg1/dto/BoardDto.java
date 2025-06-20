package com.example.prg1.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.prg1.Entity.Board}
 */
//@Value
@Data
public class BoardDto implements Serializable {
    int id;
    String title;
    String content;
    String writer;
    LocalDateTime createdAt;
}