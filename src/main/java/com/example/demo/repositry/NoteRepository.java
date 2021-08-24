package com.example.demo.repositry;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Note;


@Repository
public interface NoteRepository extends CrudRepository<Note, Integer> {
	List<Note> findByTitle(String title);
	List<Note> findByTitleContainingOrDescriptionContaining(String str, String str2);

}
