package com.example.demo.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.User;
import com.example.demo.repositry.UserRepositiry;
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
	private UserRepositiry userRepositiry;

	@Autowired
	public NoteController(NoteRepository noteRepository, UserRepositiry userRepositiry) {
		this.noteRepository = noteRepository;
		this.userRepositiry = userRepositiry;
	}
	
	@GetMapping()
	public String findAll(Model model, Principal prl) {
		User user = userRepositiry.findByUsername(prl.getName());
		Iterable<Note> notes = user.getNotes();
		model.addAttribute("notes",notes);
		return "notes";
	}
	@GetMapping("/notesForm")
	public String getNotes(){return "notesForm";}

	@PostMapping("/create")
	public String addNote(@ModelAttribute Note note, Principal prl){
		User user = userRepositiry.findByUsername(prl.getName());
		user.addNote(note);
		userRepositiry.save(user);
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
		//Iterable<Note> notes = noteRepository.findByTitleContainingOrDescriptionContaining(param, param);
		List<Note> notes = noteRepository.findNotes(param);
		model.addAttribute("notes", notes);
		return "notes";
	}
}
