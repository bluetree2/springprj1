package com.example.prg1.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Entity
@Table
@Getter
@Setter
@ToString
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String content;
    private String writer;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
