package com.example.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
	private String username;
	private String password;
	private String role;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Note> notes = new ArrayList<>();
	@Column(unique = true)
	private String email;
	
	public User() {}
	
	public User(int id, String username, String password, String role, List<Note> notes, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.notes = notes;
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Note> getNotes() {return notes;}
	public void setNotes(List<Note> notes) {this.notes = notes;}
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	public void addNote(Note note){
		note.setUser(this);
		notes.add(note);
	}
}
