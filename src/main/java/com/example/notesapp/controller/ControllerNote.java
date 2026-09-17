package com.example.notesapp.controller;

import com.example.notesapp.model.Note;
import com.example.notesapp.service.ServiceNote;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
public class ControllerNote {

    private final ServiceNote noteService;

    public ControllerNote(ServiceNote noteService) {
        this.noteService = noteService;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Note> createNote(
            @Valid @RequestPart("note") Note note,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        Note savedNote = noteService.createNote(note, image);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable UUID id) {
        return noteService.getNoteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Note> updateNote(
            @PathVariable UUID id,
            @Valid @RequestPart("note") Note noteDetails,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        return noteService.updateNote(id, noteDetails, image)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable UUID id) {
        boolean deleted = noteService.deleteNote(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}