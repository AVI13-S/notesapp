package com.example.notesapp.dto;



import com.example.notesapp.model.Note;

public class NoteMapper {

    private NoteMapper() {
    }

    public static NoteResponse toResponse(Note note) {
        return new NoteResponse(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getImageUrl()
        );
    }
}
