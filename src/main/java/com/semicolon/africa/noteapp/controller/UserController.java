package com.semicolon.africa.noteapp.controller;

import com.mongodb.internal.bulk.DeleteRequest;
import com.semicolon.africa.noteapp.data.model.Note;
import com.semicolon.africa.noteapp.dtos.request.*;
import com.semicolon.africa.noteapp.dtos.response.*;
import com.semicolon.africa.noteapp.services.UserServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@AllArgsConstructor
@RequestMapping("/api/note")
public class UserController {

    @Autowired
    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try{
            RegisterResponse response = userServices.register(registerRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.CREATED);
        } catch (Exception exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try{
            LoginResponse response = userServices.login(loginRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.CREATED);
        } catch (Exception exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody LogoutRequest logoutRequest) {
        try{
            LogoutResponse response = userServices.logout(logoutRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.CREATED);
        } catch (Exception exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByAuthor")
    public ResponseEntity<?> findByAuthor(@RequestBody GetNoteRequest getNoteRequest) {
        try {
            List<Note> notes = userServices.findAllNotesByAuthor(getNoteRequest.getEmail());
            return new ResponseEntity<>(new ApiResponse(true, notes), HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addNote")
    public ResponseEntity<?> addNote(@RequestBody AddNoteRequest addNoteRequest) {
        try{
            AddNoteResponse addNoteResponse = userServices.addNote(addNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, addNoteResponse), HttpStatus.CREATED);
        } catch (Exception exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByTitle")
    public ResponseEntity<?> findByTitle(@RequestBody GetNoteByTitleRequest getNoteByTitleRequest) {
        try {
            Note note = userServices.findNoteByTitle(getNoteByTitleRequest.getTitle());
            return new ResponseEntity<>(new ApiResponse(true, note), HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateNote(@RequestBody UpdateNoteRequest updateNoteRequest) {
        try{
            UpdateNoteResponse updateNoteResponse = userServices.update(updateNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, updateNoteResponse), HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteNote(@RequestBody GetNoteByTitleRequest getNoteByTitleRequest) {
            try {
                String deleteNote = userServices.deleteNote(getNoteByTitleRequest.getTitle());
                return new ResponseEntity<>(new ApiResponse(true, deleteNote), HttpStatus.OK);
            } catch (Exception exception){
                return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
            }
    }
}
