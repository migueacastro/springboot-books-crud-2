package com.project.backend.controllers;

import com.project.backend.domain.dto.BookDto;
import com.project.backend.domain.dto.BookDto;
import com.project.backend.domain.entities.BookEntity;
import com.project.backend.domain.entities.BookEntity;
import com.project.backend.mappers.impl.BookMapper;
import com.project.backend.services.BookService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Log
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping("/api/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookEntity> books = bookService.getAllBooks();
        List<BookDto> bookDtoStream = books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookDtoStream);
    }


    @GetMapping("/api/books/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        Optional<BookEntity> book = bookService.getBookById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(bookMapper.mapTo(book.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/api/books/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookEntity bookDetails) {
        Optional<BookEntity> book = bookService.updateBook(id, bookDetails);
        if (book.isPresent()) {
            return ResponseEntity.ok(bookMapper.mapTo(book.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/books")
    public ResponseEntity<BookDto> createBook(@RequestBody final BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity createdBook = bookService.createBook(bookEntity);
        return ResponseEntity.ok(bookMapper.mapTo(createdBook));

    }

    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookService.deleteBookById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
