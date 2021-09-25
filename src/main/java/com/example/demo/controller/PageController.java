package com.example.demo.controller;

import com.example.demo.config.SecurityConfig;
import com.example.demo.model.ConfirmationToken;
import com.example.demo.model.User;
import com.example.demo.repositry.RoleRepository;
import com.example.demo.repositry.TokenRepository;
import com.example.demo.repositry.UserRepositiry;
import com.example.demo.sevice.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.UUID;

@Controller
public class PageController {
	private UserRepositiry userRepositiry;
	private MailService mailService;
	private TokenRepository tokenRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder encoder;

	@Autowired
	public PageController(UserRepositiry userRepositiry, MailService mailService, TokenRepository tokenRepository, RoleRepository roleRepository) {
		this.userRepositiry = userRepositiry;
		this.mailService = mailService;
		this.tokenRepository = tokenRepository;
		this.roleRepository = roleRepository;
		this.encoder = new BCryptPasswordEncoder();
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
    @GetMapping("/confirm")
	public String confirm(@RequestParam("tokenValue") String tokenValue){
		ConfirmationToken token = tokenRepository.findByValue(tokenValue);
		User user = token.getUser();
		user.setEnabled(true);
		userRepositiry.save(user);
		tokenRepository.delete(token);
		return "redirect:/login";
	}
	@GetMapping("/changePassword")
	public String passwordForm(){
		return "passwordChangeForm";
	}
	@PostMapping("/remember")
	public String remember(@RequestParam("username") String username){
		User user = userRepositiry.findByUsername(username);
		SimpleMailMessage mail = new SimpleMailMessage();
		String newPassword = UUID.randomUUID().toString();
		user.setPassword(encoder.encode(newPassword));
		userRepositiry.save(user);
		mail.setText("Новый пароль вашего аккаунта: "+newPassword);
		mail.setTo(user.getEmail());
		mail.setFrom("ckj14@mail.com");
		mail.setSubject("Восстановление пароля");
		mailService.sendMail(mail);
		return "redirect:/login";
	}
	@PostMapping("/passwordComplete")
	public String changePassword(@RequestParam("password") String password, Principal prl){
		String username = prl.getName();
		User user = userRepositiry.findByUsername(username);
		if (user == null){return "redirect:/login";}
		user.setPassword(encoder.encode(password));
		userRepositiry.save(user);
		return "redirect:/";
	}
    
    @PostMapping("/regComplete")
    public String registration(@RequestParam("username") String username,
							   @RequestParam("password") String password,
							   @RequestParam("email") String email,
							   @RequestParam("url") String url) {
    	User user = userRepositiry.findByUsername(username);
    	if (user == null) {
    		User regUser = new User(0,username,encoder.encode(password),roleRepository.findByName("USER"),null,email);
    		userRepositiry.save(regUser);
			ConfirmationToken token = new ConfirmationToken(regUser);
			url = url.replace("registration","confirm");
			url = url+"tokenValue="+token.getValue();
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setText("Перейдите по ссылке для активации аккаунта: "+url);
			mail.setTo(regUser.getEmail());
			mail.setFrom("ckj14@mail.com");
			mail.setSubject("Активация аккаунта");
			mailService.sendMail(mail);
			tokenRepository.save(token);
        	return "redirect:/login";
    	}
		return "redirect:/registration";
    }

    @GetMapping("/notes")
	public String notes(){return "notes";}

	@GetMapping("/users")
	public String users(){return "userPage";}
}
