package com.semicolon.africa.noteapp.data.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isLogin;
    private List<Note> notes = new ArrayList<Note>();
    private List<Note> sharedNote = new ArrayList<>();
    private List<Note> receivedNotes = new ArrayList<>();
}
