package com.example.notesapp.repository;

import com.example.notesapp.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepositoryNote extends JpaRepository<Note, UUID> {

}