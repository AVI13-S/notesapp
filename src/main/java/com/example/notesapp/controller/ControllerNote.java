package com.example.notesapp.controller;

import com.example.notesapp.dto.NoteResponse;
import com.example.notesapp.service.ServiceNote;
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

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<NoteResponse> createNote(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        NoteResponse savedNote = noteService.createNote(title, content, image);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<NoteResponse> updateNote(
            @PathVariable UUID id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        return noteService.updateNote(id, title, content, image)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable UUID id) {
        return noteService.getNoteById(id)
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