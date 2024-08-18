package com.semicolon.africa.noteapp.services;

import com.semicolon.africa.noteapp.data.model.Note;
import com.semicolon.africa.noteapp.dtos.request.AddNoteRequest;
import com.semicolon.africa.noteapp.dtos.request.UpdateNoteRequest;
import com.semicolon.africa.noteapp.dtos.response.AddNoteResponse;
import com.semicolon.africa.noteapp.dtos.response.UpdateNoteResponse;

import java.util.List;

public interface NoteServices {

    AddNoteResponse add(AddNoteRequest addNoteRequest);

    Note findNoteByTitle(String title);

    void deleteNoteByTitle(String title);

    UpdateNoteResponse update(UpdateNoteRequest updateNoteRequest);

    void deleteAll();

    Note save(Note newNote);

    List<Note> getAllNote();
}
