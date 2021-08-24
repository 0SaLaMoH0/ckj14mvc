package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.repositry.UserRepositiry;

@Controller
@RequestMapping("/users")
public class UserController {
	private UserRepositiry userRepositiry;
	
	@Autowired
	public UserController(UserRepositiry userRepositiry) {
		this.userRepositiry = userRepositiry;
	}

	@GetMapping()
	public String findAll(Model model) {
		Iterable<User> users = userRepositiry.findAll();
		model.addAttribute("users",users);
		return "userPage";
	}

	@GetMapping("/{id}")
	public String findById() {
		return "userInfo";
	}
	
	@GetMapping("/creteForm")
	public String getUserForm() {
		return "userForm";
	}
	
	@PostMapping("/create")
	public String addUser(@ModelAttribute User user) {
		userRepositiry.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("/search")
	public String seatch(@RequestParam("str") String str, @RequestParam("age") int age) {
		//List<User> users = userRepositiry.findByUsernameContaining(param);
		//List<User> users = userRepositiry.findByAgeGreaterThan(param);
		//List<User> users = userRepositiry.findByUsernameContainingAndAgeLessThan(str, age);
		List<User> users = userRepositiry.searchtemp(str,age);
		return "userPage";
	}
}
