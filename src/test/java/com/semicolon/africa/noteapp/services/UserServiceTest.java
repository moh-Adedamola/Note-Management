package com.semicolon.africa.noteapp.services;

import com.semicolon.africa.noteapp.data.model.Note;
import com.semicolon.africa.noteapp.data.model.User;
import com.semicolon.africa.noteapp.data.repositories.NoteRepository;
import com.semicolon.africa.noteapp.dtos.request.*;
import com.semicolon.africa.noteapp.dtos.response.*;
import com.semicolon.africa.noteapp.exception.UserAlreadyExistException;
import com.semicolon.africa.noteapp.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserServices userServices;

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private NoteServices noteServices;

    @BeforeEach
    public void setUp(){
        userServices.deleteAll();
        noteServices.deleteAll();
    }

    @Test
    public void testRegisterUser() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("userFirstName");
        registerRequest.setLastName("userLastName");
        registerRequest.setEmail("myEmail");
        registerRequest.setPassword("password");
        RegisterResponse response = userServices.register(registerRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Registration Successful");
    }

    @Test
    public void testThatEmailIsUniqueToOneUser(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("userFirstName");
        registerRequest.setLastName("userLastName");
        registerRequest.setEmail("myEmail@gmail.com");
        registerRequest.setPassword("password");
        RegisterResponse response = userServices.register(registerRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Registration Successful");

        assertThrows(UserAlreadyExistException.class, ()->userServices.register(registerRequest)) ;


    }

    @Test
    public void testThatUserIsLogin(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("userFirstName");
        registerRequest.setLastName("userLastName");
        registerRequest.setEmail("myEmail@gmail.com");
        registerRequest.setPassword("password");
        RegisterResponse response = userServices.register(registerRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Registration Successful");

         LoginRequest loginRequest = new LoginRequest();
         loginRequest.setEmail("myEmail@gmail.com");
         loginRequest.setPassword("password");
         LoginResponse loginResponse = userServices.login(loginRequest);
         assertThat(loginResponse).isNotNull();
         assertThat(loginResponse.getMessage()).isEqualTo("Successfully logged in");

    }

    @Test
    public void testThatUserIsLoggedOut(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("userFirstName");
        registerRequest.setLastName("userLastName");
        registerRequest.setEmail("myEmail@gmail.com");
        registerRequest.setPassword("password");
        RegisterResponse response = userServices.register(registerRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Registration Successful");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("myEmail@gmail.com");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userServices.login(loginRequest);
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getMessage()).isEqualTo("Successfully logged in");

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setEmail("myEmail@gmail.com");
        LogoutResponse logoutResponse = userServices.logout(logoutRequest);
        assertThat(logoutResponse).isNotNull();
        assertThat(logoutResponse.getMessage()).isEqualTo("Successfully logged out");

    }

    @Test
    public void testFindUser(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("userFirstName");
        registerRequest.setLastName("userLastName");
        registerRequest.setEmail("myEmail@gmail.com");
        registerRequest.setPassword("password");
        RegisterResponse response = userServices.register(registerRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Registration Successful");

        User foundUser = userServices.findUserByEmail("myEmail@gmail.com");
        assertNotNull(foundUser);
    }

    @Test
    public void testFindUserWrongUserThrowException(){
        assertThrows(UserNotFoundException.class, ()->userServices.findUserByEmail("wrongUser@gmail.com"));
    }

    @Test
    public void testThatUserCanAddNote(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Ying");
        registerRequest.setLastName("Yang");
        registerRequest.setEmail("yang@gmail.com");
        registerRequest.setPassword("password");
        RegisterResponse response = userServices.register(registerRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Registration Successful");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("yang@gmail.com");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userServices.login(loginRequest);
        assertThat(loginResponse).isNotNull();

        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("Lord of the flies");
        addNoteRequest.setContent("Content of my note. Lord of the flies");
        addNoteRequest.setAuthorEmail("yang@gmail.com");
        AddNoteResponse noteResponse = userServices.addNote(addNoteRequest);
        assertThat(noteResponse.getMessage()).isEqualTo("Successfully added note");

    }

    @Test

    public void testThatUserCanFindNote(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("userFirstName");
        registerRequest.setLastName("userLastName");
        registerRequest.setEmail("myEmail");
        registerRequest.setPassword("password");
        RegisterResponse response = userServices.register(registerRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Registration Successful");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("myEmail");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userServices.login(loginRequest);
        assertThat(loginResponse).isNotNull();

        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("title");
        addNoteRequest.setContent("Content");
        addNoteRequest.setAuthorEmail("myEmail");
        AddNoteResponse noteResponse = userServices.addNote(addNoteRequest);
        assertThat(noteResponse.getMessage()).isEqualTo("Successfully added note");

        Note note = userServices.findNoteByTitle("title");
        assertNotNull(note);
        assertEquals(note.getTitle(),"title");


    }
    @Test
    public void testThatUserCanFindListOfNotes(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("userFirstName");
        registerRequest.setLastName("userLastName");
        registerRequest.setEmail("myEmail");
        registerRequest.setPassword("password");
        RegisterResponse response = userServices.register(registerRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Registration Successful");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("myEmail");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userServices.login(loginRequest);
        assertThat(loginResponse).isNotNull();

        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("Title");
        addNoteRequest.setContent("Content");
        addNoteRequest.setAuthorEmail("myEmail");
        AddNoteResponse noteResponse = userServices.addNote(addNoteRequest);
        assertThat(noteResponse.getMessage()).isEqualTo("Successfully added note");

        AddNoteRequest addNoteRequest2 = new AddNoteRequest();
        addNoteRequest2.setTitle("Title2");
        addNoteRequest2.setContent("Content2");
        addNoteRequest2.setAuthorEmail("myEmail");
        AddNoteResponse noteResponse2 = userServices.addNote(addNoteRequest2);
        assertThat(noteResponse2.getMessage()).isEqualTo("Successfully added note");

        List<Note> notes =  userServices.findAllNotesByAuthor("myEmail");
        assertNotNull(notes);
        assertEquals(notes.size(),2);
    }

    @Test
    public void testThatUserCanUpdateNote(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("moh");
        registerRequest.setLastName("adegbite");
        registerRequest.setEmail("adedamola13@gmail.com");
        registerRequest.setPassword("1234");
        RegisterResponse response = userServices.register(registerRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Registration Successful");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("adedamola13@gmail.com");
        loginRequest.setPassword("1234");
        LoginResponse loginResponse = userServices.login(loginRequest);
        assertThat(loginResponse).isNotNull();

        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("title");
        addNoteRequest.setContent("Updated Content of my note");
        addNoteRequest.setAuthorEmail("adedamola13@gmail.com");
        AddNoteResponse addNoteResponse = userServices.addNote(addNoteRequest);
        assertNotNull(addNoteResponse);

        Note foundNote = userServices.findNoteByTitle("title");
        assertNotNull(foundNote);

        UpdateNoteRequest updateNoteRequest = new UpdateNoteRequest();
        updateNoteRequest.setTitle("title");
        updateNoteRequest.setContent("New Content of my note");
        UpdateNoteResponse updateNoteResponse = userServices.update(updateNoteRequest);
        assertNotNull(updateNoteResponse);
        assertThat(updateNoteResponse.getMessage()).isEqualTo("Note updated successfully");
        Note updatedNote = userServices.findNoteByTitle("title");
        assertThat(updatedNote.getContent()).isEqualTo("New Content of my note");
    }

    @Test
    public void testThatUserCanDeleteNote(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("firstName");
        registerRequest.setLastName("lastName");
        registerRequest.setEmail("email");
        registerRequest.setPassword("password");
        RegisterResponse response = userServices.register(registerRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Registration Successful");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("email");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userServices.login(loginRequest);
        assertThat(loginResponse).isNotNull();

        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("title");
        addNoteRequest.setContent("Content");
        addNoteRequest.setAuthorEmail("email");
        AddNoteResponse noteResponse = userServices.addNote(addNoteRequest);
        assertThat(noteResponse.getMessage()).isEqualTo("Successfully added note");
        Note note =  userServices.findNoteByTitle("title");
        assertNotNull(note);

        userServices.deleteNote("title");
        assertEquals(0, noteRepository.count());
    }






}
