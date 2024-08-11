package com.semicolon.africa.noteapp.data.repositories;

import com.semicolon.africa.noteapp.data.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note, String> {

}
