package com.semicolon.africa.noteapp.data.repositories;

import com.semicolon.africa.noteapp.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
