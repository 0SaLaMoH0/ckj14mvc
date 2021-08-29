package com.example.demo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Note;


@Repository
public interface NoteRepository extends CrudRepository<Note, Integer> {
	List<Note> findByTitle(String title);
	List<Note> findByTitleContainingOrDescriptionContaining(String str, String str2);
	@Query("select n from Note n where n.title like :str or n.description like :str")
	List<Note> findNotes (@Param("str") String str);
}
