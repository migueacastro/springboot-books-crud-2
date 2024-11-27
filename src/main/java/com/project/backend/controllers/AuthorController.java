package com.project.backend.controllers;

import com.project.backend.domain.dto.AuthorDto;
import com.project.backend.domain.entities.AuthorEntity;
import com.project.backend.mappers.impl.AuthorMapper;
import com.project.backend.services.AuthorService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Log
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @GetMapping("/api/authors")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorEntity> authors = authorService.getAllAuthors();
        List<AuthorDto> authorDtoStream = authors.stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(authorDtoStream);
    }


    @GetMapping("/api/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        Optional<AuthorEntity> author = authorService.getAuthorById(id);
        if (author.isPresent()) {
            return ResponseEntity.ok(authorMapper.mapTo(author.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/api/authors/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @RequestBody AuthorEntity authorDetails) {
        Optional<AuthorEntity> author = authorService.updateAuthor(id, authorDetails);
        if (author.isPresent()) {
            return ResponseEntity.ok(authorMapper.mapTo(author.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody final AuthorDto authorDto) {
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity createdAuthor = authorService.createAuthor(authorEntity);
        return ResponseEntity.ok(authorMapper.mapTo(createdAuthor));

    }

    @DeleteMapping("/api/authors/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        if (authorService.deleteAuthorById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
