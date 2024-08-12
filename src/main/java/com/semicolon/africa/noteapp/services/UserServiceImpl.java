package com.semicolon.africa.noteapp.services;

import com.semicolon.africa.noteapp.data.model.Note;
import com.semicolon.africa.noteapp.data.repositories.NoteRepository;
import com.semicolon.africa.noteapp.dtos.request.*;
import com.semicolon.africa.noteapp.dtos.response.*;
import com.semicolon.africa.noteapp.data.model.User;
import com.semicolon.africa.noteapp.data.repositories.UserRepository;
import com.semicolon.africa.noteapp.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserServices{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteServices noteServices;
    @Autowired
    private NoteRepository noteRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        for (User user : userRepository.findAll()) {
            if (user.getEmail().equals(email)) {
               if(user.getPassword().equals(password)){
                   user.setLogin(true);
                   userRepository.save(user);
                   LoginResponse loginResponse = new LoginResponse();
                   loginResponse.setMessage("Successfully logged in");
                   return loginResponse;
               } else {
                   throw new UsernameOrPasswordException("Invalid Username or Password");
               }
            }
        }

        throw new UserNotFoundException("User Does Not Exist");
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        validateEmail(registerRequest.getEmail());
        User newUser = new User();
        newUser.setFirstName(registerRequest.getFirstName());
        newUser.setLastName(registerRequest.getLastName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setLogin(false);
        userRepository.save(newUser);
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setMessage("Registration Successful");
        return registerResponse;

    }


    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        String email = logoutRequest.getEmail();

        for (User user : userRepository.findAll()) {
            if (user.getEmail().equals(email)) {
                    user.setLogin(false);
                    userRepository.save(user);
                    LogoutResponse logoutResponse = new LogoutResponse();
                    logoutResponse.setMessage("Successfully logged out");
                    return logoutResponse;

            }
        }

        throw new UserNotFoundException("User Does Not Exist");
    }

    @Override
    public User findUserByEmail(String email) {
        for (User user : userRepository.findAll()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        throw new UserNotFoundException("User Does Not Exist");
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public AddNoteResponse addNote(AddNoteRequest addNoteRequest) {
        String authorEmail = addNoteRequest.getAuthorEmail();
        User user = findUserByEmail(authorEmail);

        if (user.isLogin()){
            Note newNote = new Note();
            newNote.setTitle(addNoteRequest.getTitle());
            newNote.setContent(addNoteRequest.getContent());
            newNote.setAuthorEmail(addNoteRequest.getAuthorEmail());
            noteServices.save(newNote);
            List<Note> userNotes = user.getNotes();
            userNotes.add(newNote);
            user.setNotes(userNotes);
            userRepository.save(user);
            AddNoteResponse addNoteResponse = new AddNoteResponse();
            addNoteResponse.setMessage("Successfully added note");
            return addNoteResponse;
        }
        throw new UserLoginException("You need to log in to use this service");

    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Note findNoteByTitle(String title) {
        return noteServices.findNoteByTitle(title);
    }

    @Override
    public List<Note> findAllNotesByAuthor(String myEmail) {
        User user = findUserByEmail(myEmail);
        return user.getNotes();
    }

    @Override
    public UpdateNoteResponse update(UpdateNoteRequest updateNoteRequest) {
        return noteServices.update(updateNoteRequest);
    }

    @Override
    public String deleteNote(String title) {
        Note note = findNoteByTitle(title);
        noteRepository.delete(note);
        return "Successfully deleted note";
    }

    @Override
    public ShareNoteResponse shareNote(ShareNoteRequest shareNoteRequest) {
        User sender = userRepository.findByEmail(shareNoteRequest.getSender());
        User receiver = userRepository.findByEmail(shareNoteRequest.getReceiver());
        Note note = noteServices.findNoteByTitle(shareNoteRequest.getTitle());

        List<Note> senderSharedNotes = sender.getSharedNote();
        senderSharedNotes.add(note);
        sender.setNotes(senderSharedNotes);
        userRepository.save(sender);

        List<Note> receiverSharedNotes = receiver.getSharedNote();
        receiverSharedNotes.add(note);
        receiver.setSharedNote(receiverSharedNotes);
        userRepository.save(receiver);

        ShareNoteResponse shareNoteResponse = new ShareNoteResponse();
        shareNoteResponse.setMessage("Successfully shared note");
        return shareNoteResponse;
    }

    public void validateEmail(String email) {
        for (User user : userRepository.findAll()) {
            if (user.getEmail().equals(email)) {
                throw new UserAlreadyExistException("User with same email already exist");
            }
        }
    }



}
