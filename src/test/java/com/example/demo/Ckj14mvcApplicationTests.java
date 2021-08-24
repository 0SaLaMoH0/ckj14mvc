package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class Ckj14mvcApplicationTests {

	@Test
	void passwordEncoderTest(){
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String str = "ivan";
		String encodedStr = passwordEncoder.encode(str);
		System.out.println(encodedStr);
		System.out.println(passwordEncoder.matches(str,encodedStr));
	}
}
