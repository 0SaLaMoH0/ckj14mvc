package com.example.demo.repositry;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
public interface UserRepositiry extends CrudRepository<User, Integer>{
	User findByUsername(String name);
}
