package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repositry.NoteRepository;
import com.example.demo.repositry.UserRepositiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/users")
public class AdminController {
    private UserRepositiry userRepositiry;
    private NoteRepository noteRepository;

    @Autowired
    public AdminController(UserRepositiry userRepositiry, NoteRepository noteRepository) {
        this.userRepositiry = userRepositiry;
        this.noteRepository = noteRepository;
    }

    @GetMapping("/all")
    public List<User> getAll(){
        return (List<User>) userRepositiry.findAll();
    }
/*
    @GetMapping("/userNotes")
    public List<Note> getUserNotes(){
        return userRepositiry.findByUsername().getNotes();
    }*/
}
