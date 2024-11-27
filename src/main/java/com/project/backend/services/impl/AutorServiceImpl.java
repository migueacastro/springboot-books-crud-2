package com.project.backend.services.impl;

import com.project.backend.domain.entities.AuthorEntity;
import com.project.backend.domain.entities.BookEntity;
import com.project.backend.repositories.AuthorRepository;
import com.project.backend.repositories.BookRepository;
import com.project.backend.services.AuthorService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Log
public class AutorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public AutorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<AuthorEntity> getAllAuthors() {
        return (List<AuthorEntity>) authorRepository.findAll();
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        Set<BookEntity> books = new HashSet<>();
        for (BookEntity book : authorEntity.getBooks()) {
            BookEntity bookEntity = bookRepository.findById(book.getId()).orElse(null);
            if (bookEntity != null) {
                books.add(bookEntity);
            } else {
                log.severe("Book not found: " + book.getId());
            }
        }
        authorEntity.setBooks(books);
        return authorRepository.save(authorEntity);
    }

    @Override
    public Optional<AuthorEntity> updateAuthor(Long id, AuthorEntity authorDetails) {
        Optional<AuthorEntity> currentAuthor = authorRepository.findById(id);
        if (currentAuthor.isPresent()) {
            AuthorEntity newAuthor = currentAuthor.get();
            newAuthor.setName(authorDetails.getName());
            newAuthor.setBirthdate(authorDetails.getBirthdate());

            // Update the books
            Set<BookEntity> books = new HashSet<>();
            for (BookEntity book : authorDetails.getBooks()) {
                BookEntity bookEntity = bookRepository.findById(book.getId()).orElse(null);
                if (bookEntity != null) {
                    books.add(bookEntity);
                } else {
                    log.severe("Book not found: " + book.getId());
                }
            }
            newAuthor.setBooks(books);

            return Optional.of(authorRepository.save(newAuthor));
        } else {
            return Optional.empty();
        }
    }


    @Override
    public Optional<AuthorEntity> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Boolean deleteAuthorById(Long id) {
        if (authorRepository.findById(id).isPresent()) {
            authorRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}


