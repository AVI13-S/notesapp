package com.example.notesapp.service;

import com.example.notesapp.dto.NoteMapper;
import com.example.notesapp.dto.NoteResponse;
import com.example.notesapp.model.Note;
import com.example.notesapp.repository.RepositoryNote;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceNote {

    private final RepositoryNote noteRepository;
    private final S3Service s3Service;

    public ServiceNote(RepositoryNote noteRepository, S3Service s3Service) {
        this.noteRepository = noteRepository;
        this.s3Service = s3Service;
    }

    public NoteResponse createNote(String title, String content, MultipartFile image) {
        validate(title, content);

        String imageUrl = s3Service.uploadFile(image);
        Note note = new Note(title.trim(), content.trim(), imageUrl);
        Note saved = noteRepository.save(note);
        return NoteMapper.toResponse(saved);
    }

    public List<NoteResponse> getAllNotes() {
        return noteRepository.findAll().stream()
                .map(NoteMapper::toResponse)
                .toList();
    }

    public Optional<NoteResponse> getNoteById(UUID id) {
        return noteRepository.findById(id).map(NoteMapper::toResponse);
    }

    public Optional<NoteResponse> updateNote(UUID id, String title, String content, MultipartFile image) {
        validate(title, content);

        return noteRepository.findById(id).map(existingNote -> {
            existingNote.setTitle(title.trim());
            existingNote.setContent(content.trim());

            if (image != null && !image.isEmpty()) {
                s3Service.deleteFile(existingNote.getImageUrl());
                existingNote.setImageUrl(s3Service.uploadFile(image));
            }

            Note saved = noteRepository.save(existingNote);
            return NoteMapper.toResponse(saved);
        });
    }

    public boolean deleteNote(UUID id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isEmpty()) {
            return false;
        }

        s3Service.deleteFile(note.get().getImageUrl());
        noteRepository.deleteById(id);
        return true;
    }

    private void validate(String title, String content) {
        if (title == null || title.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title should not be empty");
        }
        if (content == null || content.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Content should not be empty");
        }
    }
}