package com.project.backend.services;

import com.project.backend.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookEntity> getAllBooks();
    BookEntity createBook(BookEntity bookEntity);
    Optional<BookEntity> updateBook(Long id, BookEntity bookDetails);
    Optional<BookEntity> getBookById(Long id);
    Boolean deleteBookById(Long id);
}
