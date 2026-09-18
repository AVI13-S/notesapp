package com.example.notesapp.dto;



import java.util.UUID;

public class NoteResponse {

    private UUID id;
    private String title;
    private String content;
    private String imageUrl;

    public NoteResponse(UUID id, String title, String content, String imageUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
