package com.example.demo.sevice;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repositry.UserRepositiry;

@Service
public class MyUserDetailsServise implements UserDetailsService{
	
	private UserRepositiry userRepositiry;
	
	public MyUserDetailsServise(UserRepositiry userRepositiry) {
		super();
		this.userRepositiry = userRepositiry;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepositiry.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.roles(user.getRole().getName())
				.disabled(!user.isEnabled())
				.build();
		return userDetails;
	}
	
}
