package com.example.demo.controller;

import com.example.demo.model.Note;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repositry.NoteRepository;
import com.example.demo.repositry.RoleRepository;
import com.example.demo.repositry.UserRepositiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class AdminController {
    private UserRepositiry userRepositiry;
    private NoteRepository noteRepository;
    private RoleRepository roleRepository;

    @Autowired
    public AdminController(UserRepositiry userRepositiry, NoteRepository noteRepository, RoleRepository roleRepository) {
        this.userRepositiry = userRepositiry;
        this.noteRepository = noteRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/all")
    public List<EntityModel<User>> getAll(){
        List<User> users = (List<User>) userRepositiry.findAll();
        List<EntityModel<User>> u = users.stream().map(userMapper()).collect(Collectors.toList());
        return u;
    }

    @GetMapping("/one/{username}")
    public EntityModel<User> getUser(@PathVariable String username){
        User user = userRepositiry.findByUsername(username);
        return EntityModel.of(user,
            linkTo(methodOn(AdminController.class).getAll()).withRel("all"),
            linkTo(methodOn(AdminController.class).getUser(user.getUsername())).withRel("one"),
            linkTo(methodOn(AdminController.class).getUserNotes(user.getUsername())).withRel("notes")
        );
    }

    @GetMapping("/userNotes/{username}")
    public List<Note> getUserNotes(@PathVariable String username){
        return userRepositiry.findByUsername(username).getNotes();
    }

    @GetMapping("/{username}/{roleName}")
    public EntityModel<User> updateUserRole(@PathVariable String username, @PathVariable String roleName){
        User user = userRepositiry.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        if (role != null){
            user.setRole(roleRepository.findByName(roleName));
            userRepositiry.save(user);
        }
        return EntityModel.of(user,
                linkTo(methodOn(AdminController.class).getAll()).withRel("all"),
                linkTo(methodOn(AdminController.class).getUser(user.getUsername())).withRel("one"),
                linkTo(methodOn(AdminController.class).getUserNotes(user.getUsername())).withRel("notes")
        );
    }

    public static Function<User, EntityModel<User>> userMapper() {
        Function<User, EntityModel<User>> mapper = u -> {
            return EntityModel.of(u,
                    linkTo(methodOn(AdminController.class).getAll()).withRel("all"),
                    linkTo(methodOn(AdminController.class).getUser(u.getUsername())).withRel("one"),
                    linkTo(methodOn(AdminController.class).getUserNotes(u.getUsername())).withRel("notes")
            );
        };
        return mapper;
    }
}
