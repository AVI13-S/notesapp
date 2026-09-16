
package com.example.notesapp.controller;

import com.example.notesapp.model.Note;
import com.example.notesapp.repository.RepositoryNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
public class ControllerNote {

    @Autowired
    private RepositoryNote noteRepository;
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note savedNote = noteRepository.save(note);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }
}
