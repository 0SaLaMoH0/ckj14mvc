package com.example.demo.controller;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Note;
import com.example.demo.repositry.NoteRepository;

@Controller
@RequestMapping("/notes")
public class NoteController {
	private NoteRepository noteRepository;

	@Autowired
	public NoteController(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}
	
	@GetMapping()
	public String findAll(Model model) {
		Iterable<Note> notes = noteRepository.findAll();
		model.addAttribute("notes",notes);
		return "notes";
	}
	@GetMapping("/notesForm")
	public String getNotes(){return "notesForm";}

	@PostMapping("/create")
	public String addNote(@ModelAttribute Note note){
		noteRepository.save(note);
		return "redirect:/notes";
	}

	@PostMapping("/updateForm")
	public String updateForm(@RequestParam("id") int id, Model model){
		Optional<Note> opt = noteRepository.findById(id);
		if (!opt.isEmpty()) {
			Note note = opt.get();
			model.addAttribute("note", note);
		}
		return "noteUpdateForm";
	}

	@PostMapping("/update")
	public String updateNote(@ModelAttribute Note n){
		Optional<Note> opt = noteRepository.findById(n.getId());
		if (!opt.isEmpty()){
			Note note = opt.get();
			note.setTitle(n.getTitle());
			note.setDescription(n.getDescription());
			noteRepository.save(note);
		}
		return "redirect:/notes";
	}

	@GetMapping("/delete")
	public String deleteNote(@RequestParam("id") int id){
		Optional<Note> note = noteRepository.findById(id);
		if (!note.isEmpty()) {
			noteRepository.delete(note.get());
		}
		return "redirect:/notes";
	}

	@GetMapping("/get")
	public String getNote(@RequestParam("id") int id, Model model){
		Optional<Note> opt = noteRepository.findById(id);
		if (!opt.isEmpty()) {
			Note[] ANotes = {opt.get()};
			Iterable<Note> notes = Arrays.asList(ANotes);
			model.addAttribute("notes", notes);
		}
		return "notes";
	}

	@GetMapping("/search")
	public String search(@RequestParam("param") String param, Model model) {
		//List<Note> list = noteRepository.findByTitle(param);
		Iterable<Note> notes = noteRepository.findByTitleContainingOrDescriptionContaining(param, param);
		model.addAttribute("notes", notes);
		return "notes";
	}
}
