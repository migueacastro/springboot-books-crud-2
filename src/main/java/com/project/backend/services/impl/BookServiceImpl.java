package com.project.backend.services.impl;

import com.project.backend.domain.entities.AuthorEntity;
import com.project.backend.domain.entities.CategoryEntity;
import com.project.backend.domain.entities.BookEntity;
import com.project.backend.repositories.AuthorRepository;
import com.project.backend.repositories.CategoryRepository;
import com.project.backend.repositories.BookRepository;
import com.project.backend.services.BookService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Log
public class BookServiceImpl implements BookService {
    private CategoryRepository categoryRepository;
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public BookServiceImpl(CategoryRepository categoryRepository, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<BookEntity> getAllBooks() {
        return (List<BookEntity>) bookRepository.findAll();
    }

    @Override
    public BookEntity createBook(BookEntity bookEntity) {
        // Ensure the category exists
        CategoryEntity category = bookEntity.getCategory();
        category = categoryRepository.findById(category.getId()).orElse(null);
        if (category == null) {
            log.severe("Category not found");
            return null;
        }
        bookEntity.setCategory(category);

        // Ensure the authors exist and set them
        Set<AuthorEntity> authors = new HashSet<>();
        for (AuthorEntity author : bookEntity.getAuthors()) {
            AuthorEntity authorEntity = authorRepository.findById(author.getId()).orElse(null);
            if (authorEntity != null) {
                authors.add(authorEntity);
            } else {
                log.severe("Author not found: " + author.getId());
            }
        }
        bookEntity.setAuthors(authors);

        return bookRepository.save(bookEntity);
    }

    @Override
    public Optional<BookEntity> updateBook(Long id, BookEntity bookEntity) {
        Optional<BookEntity> currentBook = getBookById(id);
        if (currentBook.isPresent()) {
            BookEntity newBook = currentBook.get();

            // Update fields with the provided bookEntity details
            newBook.setName(bookEntity.getName());
            newBook.setDescription(bookEntity.getDescription());

            // Ensure the category exists and set it
            CategoryEntity category = bookEntity.getCategory();
            category = categoryRepository.findById(category.getId()).orElse(null);
            if (category == null) {
                log.severe("Category not found");
                return Optional.empty();
            }
            newBook.setCategory(category);

            // Ensure the authors exist and set them
            Set<AuthorEntity> authors = new HashSet<>();
            for (AuthorEntity author : bookEntity.getAuthors()) {
                AuthorEntity authorEntity = authorRepository.findById(author.getId()).orElse(null);
                if (authorEntity != null) {
                    authors.add(authorEntity);
                } else {
                    log.severe("Author not found: " + author.getId());
                }
            }
            newBook.setAuthors(authors);

            return Optional.of(bookRepository.save(newBook));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<BookEntity> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Boolean deleteBookById(Long id) {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
