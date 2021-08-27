package com.example.demo;

import com.example.demo.sevice.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class Ckj14mvcApplicationTests {
	private MailService mailService;

	@Autowired
	public Ckj14mvcApplicationTests(MailService mailService) {
		this.mailService = mailService;
	}

	@Test
	void passwordEncoderTest(){
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String str = "admin";
		String encodedStr = passwordEncoder.encode(str);
		System.out.println(encodedStr);
		System.out.println(passwordEncoder.matches(str,encodedStr));
	}

	@Test
	void mailSender(){
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setText("Hellow mail trap");
		mail.setFrom("MarusWorld@ukr.net");
		mail.setTo("f6997caf9b-27f6ff@inbox.mailtrap.io");
		mail.setSubject("My test mail");
		System.out.println("start sending");
		mailService.sendMail(mail);
		System.out.println("end sending");
	}
}
