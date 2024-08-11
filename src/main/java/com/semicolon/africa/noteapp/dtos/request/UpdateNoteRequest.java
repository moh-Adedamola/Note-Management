package com.semicolon.africa.noteapp.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNoteRequest {
    private String title;
    private String content;

}
