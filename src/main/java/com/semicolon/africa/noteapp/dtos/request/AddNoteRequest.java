package com.semicolon.africa.noteapp.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddNoteRequest {
    private String title;
    private String content;
    private String authorEmail;
}
