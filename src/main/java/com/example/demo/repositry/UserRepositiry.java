package com.example.demo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
public interface UserRepositiry extends CrudRepository<User, Integer>{
	User findByUsername(String name);
	List<User> findAllByUsername(String name);
	List<User> findByAge(int age);
	List<User> findByUsernameContaining(String str);
	List<User> findByAgeGreaterThan(int age);
	List<User> findByUsernameContainingAndAgeLessThan(String str, int age);
	
	@Query("SELECT u FROM User u WHERE u.age > :param")
	List<User> search(@Param("param") int age);
	
	@Query("SELECT u FROM User u WHERE u.age > :age AND u.username LIKE :str")
	List<User> searchtemp(@Param("str") String str, @Param("age") int age);
}
