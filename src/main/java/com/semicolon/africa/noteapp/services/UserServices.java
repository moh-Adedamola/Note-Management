package com.semicolon.africa.noteapp.services;

import com.semicolon.africa.noteapp.data.model.Note;
import com.semicolon.africa.noteapp.data.model.User;
import com.semicolon.africa.noteapp.dtos.request.*;
import com.semicolon.africa.noteapp.dtos.response.*;

import java.util.List;

public interface UserServices {


    LoginResponse login(LoginRequest loginRequest);

    RegisterResponse register(RegisterRequest registerRequest);
    
    LogoutResponse logout(LogoutRequest logoutRequest);

    User findUserByEmail(String email);

    void deleteAll();

    AddNoteResponse addNote(AddNoteRequest addNoteRequest);

    User save(User user);

    Note findNoteByTitle(String title);

    List<Note> findAllNotesByAuthor(String myEmail);

    UpdateNoteResponse update(UpdateNoteRequest updateNoteRequest);

    String deleteNote(String title);

    ShareNoteResponse shareNote(ShareNoteRequest shareNoteRequest);
}
