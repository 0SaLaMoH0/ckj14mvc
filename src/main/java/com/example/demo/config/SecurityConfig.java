package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.sevice.MyUserDetailsServise;

import lombok.AllArgsConstructor;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private MyUserDetailsServise userDetailsServise;
	
	@Autowired
    public SecurityConfig(MyUserDetailsServise userDetailsServise) {
		super();
		this.userDetailsServise = userDetailsServise;
	}
	@Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").anonymous()
                .antMatchers("/notes/**").authenticated()
                .antMatchers("/users/**").hasRole("ADMIN")
                .and().formLogin().loginPage("/login")
                .and().rememberMe().userDetailsService(userDetailsServise)
                .and().logout()
                .and().csrf().disable();

    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	provider.setPasswordEncoder(passwordEncoder());
    	provider.setUserDetailsService(userDetailsServise);
    	auth.authenticationProvider(provider);
    }
    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetails user1 = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER").build();
        UserDetails user2 = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN").build();
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser(user1).withUser(user2);
    }
    */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
