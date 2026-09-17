package com.example.notesapp.service;
import com.example.notesapp.model.Note;
import com.example.notesapp.repository.RepositoryNote;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceNote {

    private final RepositoryNote noteRepository;

    public ServiceNote(RepositoryNote noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Optional<Note> getNoteById(UUID id) {
        return noteRepository.findById(id);
    }

    public Optional<Note> updateNote(UUID id, Note noteDetails) {
        Optional<Note> existingNoteOptional = noteRepository.findById(id);

        if (existingNoteOptional.isEmpty()) {
            return Optional.empty();
        }

        Note existingNote = existingNoteOptional.get();
        existingNote.setTitle(noteDetails.getTitle());
        existingNote.setContent(noteDetails.getContent());
        Note updatedNote = noteRepository.save(existingNote);
        return Optional.of(updatedNote);
    }

    public boolean deleteNote(UUID id) {
        if (!noteRepository.existsById(id)) {
            return false;
        }
        noteRepository.deleteById(id);
        return true;
    }
}
