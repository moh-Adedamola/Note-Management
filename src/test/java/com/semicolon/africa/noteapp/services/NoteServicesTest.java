package com.semicolon.africa.noteapp.services;

import com.semicolon.africa.noteapp.data.model.Note;
import com.semicolon.africa.noteapp.data.repositories.NoteRepository;
import com.semicolon.africa.noteapp.dtos.request.AddNoteRequest;
import com.semicolon.africa.noteapp.dtos.request.UpdateNoteRequest;
import com.semicolon.africa.noteapp.dtos.response.AddNoteResponse;
import com.semicolon.africa.noteapp.dtos.response.UpdateNoteResponse;
import com.semicolon.africa.noteapp.exception.NoteNotFoundException;
import com.semicolon.africa.noteapp.exception.NoteWithSameTitleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NoteServicesTest {

    @Autowired
    private NoteServices noteServices;
    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach

    public void setUp() {
        noteServices.deleteAll();
    }

    @Test
    public void testAddNote(){
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("Title");
        addNoteRequest.setContent("Content of my note");
        addNoteRequest.setAuthorEmail("email");
        AddNoteResponse addNoteResponse = noteServices.add(addNoteRequest);
        assertNotNull(addNoteResponse);
        assertThat(addNoteResponse.getMessage()).isEqualTo("note added successfully");
    }

    @Test
    public void testThatNoteWithSameTitleThrowsException(){
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("Title");
        addNoteRequest.setContent("Content of my note");
        addNoteRequest.setAuthorEmail("email");
        AddNoteResponse addNoteResponse = noteServices.add(addNoteRequest);
        assertNotNull(addNoteResponse);
        assertThat(addNoteResponse.getMessage()).isEqualTo("note added successfully");

        assertThrows(NoteWithSameTitleException.class, () -> noteServices.add(addNoteRequest));
    }

    @Test
    public void testFindNoteByTitle(){
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("title");
        addNoteRequest.setContent("Content of my note");
        addNoteRequest.setAuthorEmail("email");
        AddNoteResponse addNoteResponse = noteServices.add(addNoteRequest);
        assertNotNull(addNoteResponse);
        assertThat(addNoteResponse.getMessage()).isEqualTo("note added successfully");

        Note foundNote = noteServices.findNoteByTitle("title");
        assertNotNull(foundNote);
    }

    @Test
    public void testFindNoteWrongTitle(){
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("Title");
        addNoteRequest.setContent("Content of my note");
        addNoteRequest.setAuthorEmail("email");
        AddNoteResponse addNoteResponse = noteServices.add(addNoteRequest);
        assertNotNull(addNoteResponse);
        assertThat(addNoteResponse.getMessage()).isEqualTo("note added successfully");

        assertThrows(NoteNotFoundException.class, ()->noteServices.findNoteByTitle("wrongTitle"));
    }

    @Test
    public void deleteNoteByTitle(){
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("title");
        addNoteRequest.setContent("Content of my note");
        addNoteRequest.setAuthorEmail("email");
        AddNoteResponse addNoteResponse = noteServices.add(addNoteRequest);
        assertNotNull(addNoteResponse);
        assertThat(addNoteResponse.getMessage()).isEqualTo("note added successfully");

        Note foundNote = noteServices.findNoteByTitle("title");
        assertNotNull(foundNote);

        noteServices.deleteNoteByTitle("title");
        assertEquals(0, noteRepository.count());

    }

    @Test
    void testThatAddedNoteCanBeUpdated() {
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("title");
        addNoteRequest.setContent("Updated Content of my note");
        addNoteRequest.setAuthorEmail("email");
        AddNoteResponse addNoteResponse = noteServices.add(addNoteRequest);
        assertNotNull(addNoteResponse);

        Note foundNote = noteServices.findNoteByTitle("title");
        assertNotNull(foundNote);

        UpdateNoteRequest updateNoteRequest = new UpdateNoteRequest();
        updateNoteRequest.setTitle("title");
        updateNoteRequest.setContent("New Content of my note");
        UpdateNoteResponse updateNoteResponse = noteServices.update(updateNoteRequest);
        assertNotNull(updateNoteResponse);
        assertThat(updateNoteResponse.getMessage()).isEqualTo("Note updated successfully");
        Note updatedNote = noteServices.findNoteByTitle("title");
        assertThat(updatedNote.getContent()).isEqualTo("New Content of my note");

    }




}