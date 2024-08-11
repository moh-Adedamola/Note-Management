package com.semicolon.africa.noteapp.exception;

public class NoteWithSameTitleException extends RuntimeException {
    public NoteWithSameTitleException(String message) {
        super(message);
    }
}
