package com.project.backend.services.impl;

import com.project.backend.domain.entities.CategoryEntity;
import com.project.backend.domain.entities.BookEntity;
import com.project.backend.repositories.CategoryRepository;
import com.project.backend.repositories.BookRepository;
import com.project.backend.services.BookService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log
public class BookServiceImpl implements BookService {
    private CategoryRepository categoryRepository;
    private BookRepository bookRepository;

    public BookServiceImpl(CategoryRepository categoryRepository, BookRepository bookRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(BookEntity bookEntity) {
        CategoryEntity categoryId = bookEntity.getCategory();
        categoryId = categoryRepository.findById(categoryId.getId()).orElse(null);
        if (categoryId == null) {
            log.severe("Author not found");
            return null;
        }
        bookEntity.setCategory(categoryId);
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> getAllBooks() {
        return (List<BookEntity>) bookRepository.findAll();
    }

    @Override
    public Optional<BookEntity> updateBook(Long id, BookEntity bookEntity) {
        Optional<BookEntity> currentBook= getBookById(id);
        if (currentBook.isPresent()) {
            BookEntity newBook = currentBook.get();
            newBook.setName(currentBook.get().getName());
            newBook.setDescription(currentBook.get().getDescription());
            newBook.setCategory(currentBook.get().getCategory());

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
