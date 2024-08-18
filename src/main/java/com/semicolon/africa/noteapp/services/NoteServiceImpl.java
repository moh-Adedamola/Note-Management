package com.semicolon.africa.noteapp.services;

import com.semicolon.africa.noteapp.data.model.Note;
import com.semicolon.africa.noteapp.data.repositories.NoteRepository;
import com.semicolon.africa.noteapp.dtos.request.AddNoteRequest;
import com.semicolon.africa.noteapp.dtos.request.UpdateNoteRequest;
import com.semicolon.africa.noteapp.dtos.response.AddNoteResponse;
import com.semicolon.africa.noteapp.dtos.response.UpdateNoteResponse;
import com.semicolon.africa.noteapp.exception.NoteNotFoundException;
import com.semicolon.africa.noteapp.exception.NoteWithSameTitleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class NoteServiceImpl implements NoteServices {
    @Autowired
    private NoteRepository noteRepository;


    @Override
    public AddNoteResponse add(AddNoteRequest addNoteRequest) {
        Note note = new Note();
        validateTitle(addNoteRequest.getTitle());
        note.setTitle(addNoteRequest.getTitle());
        note.setContent(addNoteRequest.getContent());
        note.setAuthorEmail(addNoteRequest.getAuthorEmail());
        noteRepository.save(note);
        AddNoteResponse addNoteResponse = new AddNoteResponse();
        addNoteResponse.setMessage("note added successfully");
        return addNoteResponse;
    }

    @Override
    public Note findNoteByTitle(String title) {
        Note note = noteRepository.findByTitle(title);
        if(!note.getTitle().equals(title)) throw new NoteNotFoundException("Note not found");
        return note;
    }

    @Override
    public void deleteNoteByTitle(String title) {
        Note note = findNoteByTitle(title);
        noteRepository.delete(note);
    }



    @Override
    public UpdateNoteResponse update(UpdateNoteRequest updateNoteRequest) {
        Note note = findNoteByTitle(updateNoteRequest.getTitle().toLowerCase());
        note.setContent(updateNoteRequest.getContent());
        noteRepository.save(note);
        UpdateNoteResponse updateNoteResponse = new UpdateNoteResponse();
        updateNoteResponse.setMessage("Note updated successfully");
        return updateNoteResponse;
    }

    @Override
    public void deleteAll() {
        noteRepository.deleteAll();
    }

    @Override
    public Note save(Note newNote) {
        return noteRepository.save(newNote);
    }

    @Override
    public List<Note> getAllNote() {
        return noteRepository.findAll();
    }

    public void validateTitle(String title){
        for (Note note : noteRepository.findAll()) {
            if(note.getTitle().equals(title)){
                throw new NoteWithSameTitleException("Note with same title already exist");
            }
        }
    }


}
