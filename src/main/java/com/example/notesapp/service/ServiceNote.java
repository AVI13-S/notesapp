package com.example.notesapp.service;

import com.example.notesapp.model.Note;
import com.example.notesapp.repository.RepositoryNote;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public Note createNote(Note note, MultipartFile image) {
        String imageUrl = s3Service.uploadFile(image);
        if (imageUrl != null) {
            note.setImageUrl(imageUrl);
        }
        return noteRepository.save(note);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Optional<Note> getNoteById(UUID id) {
        return noteRepository.findById(id);
    }

    public Optional<Note> updateNote(UUID id, Note noteDetails, MultipartFile image) {
        return noteRepository.findById(id).map(existingNote -> {
            existingNote.setTitle(noteDetails.getTitle());
            existingNote.setContent(noteDetails.getContent());

            String imageUrl = s3Service.uploadFile(image);
            if (imageUrl != null) {
                existingNote.setImageUrl(imageUrl);
            }

            return noteRepository.save(existingNote);
        });
    }

    public boolean deleteNote(UUID id) {
        if (!noteRepository.existsById(id)) {
            return false;
        }
        noteRepository.deleteById(id);
        return true;
    }
}