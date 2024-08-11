package com.semicolon.africa.noteapp.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ApiResponse {
    boolean success;
    Object response;
}
