package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.repositry.UserRepositiry;

@Controller
public class PageController {
	private UserRepositiry userRepositiry;

	@Autowired
	public PageController(UserRepositiry userRepositiry) {
		this.userRepositiry = userRepositiry;
	}
	
    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/registration")
    public String regForm() {
    	return "registrationForm";
    }
    
    @PostMapping("/regComplete")
    public String registration(@RequestParam("username") String username, @RequestParam("password") String password) {
    	User user = userRepositiry.findByUsername(username);
    	if (user == null) {
    		User regUser = new User();
    		regUser.setUsername(username);
    		regUser.setRole("USER");
    		regUser.setPassword(new BCryptPasswordEncoder().encode(password));
    		userRepositiry.save(regUser);
        	return "redirect:/login";
    	}
		return "redirect:/registration";
    }
}
