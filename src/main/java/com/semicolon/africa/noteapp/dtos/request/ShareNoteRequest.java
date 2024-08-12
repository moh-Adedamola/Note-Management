package com.semicolon.africa.noteapp.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ShareNoteRequest {
    private String title;
    private String sender;
    private String receiver;

}
