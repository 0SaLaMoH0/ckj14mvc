package com.example.demo.controller;

import com.example.demo.exeptions.NoSuchNoteExeption;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NoteControllerAdvice {

    @ResponseBody
    @ExceptionHandler(NoSuchNoteExeption.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String noteNotFoundHandler(NoSuchNoteExeption exeption){
        return exeption.getMessage();
    }
}
