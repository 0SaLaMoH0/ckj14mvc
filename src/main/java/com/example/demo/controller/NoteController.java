package com.example.demo.controller;

import com.example.demo.exeptions.NoSuchNoteExeption;
import com.example.demo.model.Note;
import com.example.demo.model.User;
import com.example.demo.repositry.NoteRepository;
import com.example.demo.repositry.UserRepositiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private NoteRepository noteRepository;
    private UserRepositiry userRepositiry;

    @Autowired
    public NoteController(NoteRepository noteRepository, UserRepositiry userRepositiry) {
        this.noteRepository = noteRepository;
        this.userRepositiry = userRepositiry;
    }

    @GetMapping("/all")
    public List<EntityModel<Note>> getAllNotes(Principal prl){
        User user = userRepositiry.findByUsername(prl.getName());
        return user.getNotes().stream().map(getMapper(prl)).collect(Collectors.toList());
    }

    @GetMapping("/one/{id}")
    public EntityModel<Note> getNote(@PathVariable int id, Principal prl){
        Note note = noteRepository.findByIdAndUserUsername(id,prl.getName()).orElseThrow(()->new NoSuchNoteExeption(id));
        var result = EntityModel.of(note,
                linkTo(methodOn(NoteController.class).getAllNotes(prl)).withRel("notes"),
                linkTo(methodOn(NoteController.class).deleteNote(id,prl)).withRel("notes")
        );
        return result;
    }

    @PostMapping("/create")
    public EntityModel<Note> createNote(@RequestBody Note note, Principal prl){
        User user = userRepositiry.findByUsername(prl.getName());
        user.addNote(note);
        note = noteRepository.save(note);
        userRepositiry.save(user);
        return getMapper(prl).apply(note);
    }

    @PutMapping("/update")
    public Note updateNote(@RequestBody Note note, Principal prl){
        Note oldNote = noteRepository.findByIdAndUserUsername(note.getId(), prl.getName()).orElseThrow(() -> new NoSuchNoteExeption(note.getId()));
        oldNote.setDate(new Date().toString());
        oldNote.setTitle(note.getTitle());
        oldNote.setDescription(note.getDescription());
        return noteRepository.save(oldNote);
    }

    @DeleteMapping("/delete/{id}")
    public Note deleteNote(@PathVariable int id, Principal prl){
        Note note = noteRepository.findByIdAndUserUsername(id, prl.getName()).orElseThrow(()->new NoSuchNoteExeption(id));
        noteRepository.delete(note);
        return note;
    }

    public static Function<Note,EntityModel<Note>> getMapper(Principal prl) {
        Function<Note, EntityModel<Note>> mapper = n -> {
            return EntityModel.of(n,
                    linkTo(methodOn(NoteController.class).getNote(n.getId(), prl)).withRel("get"),
                    linkTo(methodOn(NoteController.class).updateNote(n, prl)).withRel("put"),
                    linkTo(methodOn(NoteController.class).deleteNote(n.getId(), prl)).withRel("delete")
            );
        };
        return mapper;
    }
}