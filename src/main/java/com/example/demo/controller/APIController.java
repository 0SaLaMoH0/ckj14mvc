package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repositry.UserRepositiry;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class APIController {
    private UserRepositiry userRepositiry;

    @GetMapping("/usernames")
    public List<String> getUserNames(){
        List<User> users = (List<User>) userRepositiry.findAll();
        return users.stream().map(u -> u.getUsername()).collect(Collectors.toList());
    }
    @GetMapping("/email")
    public Boolean isEmailNotUsed(@RequestParam("email") String email){
        User user = userRepositiry.findByEmail(email);
        if (user == null){return true;}
        return false;
    }
}
