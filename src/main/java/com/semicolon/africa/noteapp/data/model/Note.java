package com.semicolon.africa.noteapp.data.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
@Setter
@Getter
public class Note {
    @Id
    private String id;
    private String title;
    private String content;
    private String authorEmail;
    private LocalDateTime createdAt=LocalDateTime.now();

}
