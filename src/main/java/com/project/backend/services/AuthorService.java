package com.project.backend.services;

import com.project.backend.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<AuthorEntity> getAllAuthors();
    AuthorEntity createAuthor(AuthorEntity authorEntity);
    Optional<AuthorEntity> updateAuthor(Long id, AuthorEntity authorDetails);
    Optional<AuthorEntity> getAuthorById(Long id);
    Boolean deleteAuthorById(Long id);
}
