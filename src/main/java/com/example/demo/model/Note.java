package com.example.demo.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "notes", indexes = {
		@Index(name = "idx_note_date", columnList = "date")
})
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	private String date;
	
	public Note () {}
	
	public Note(int id, String title, String description, User user, String date) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.user = user;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {return user;}

	public void setUser(User user) {this.user = user;}

	public String getDate() {return date;}

	public void setDate(String date) {this.date = date;}

	@Override
	public String toString() {
		return "Note [id=" + id + ", title=" + title + ", description=" + description + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		return Objects.equals(description, other.description) && id == other.id && Objects.equals(title, other.title);
	}
}
