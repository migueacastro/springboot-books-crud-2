package com.project.backend.mappers.impl;

import com.project.backend.domain.dto.AuthorDto;
import com.project.backend.domain.dto.BookDto;
import com.project.backend.domain.entities.AuthorEntity;
import com.project.backend.domain.entities.BookEntity;
import com.project.backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorMapper implements Mapper<AuthorEntity, AuthorDto> {
    private ModelMapper modelMapper;

    public AuthorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(AuthorEntity author) {
        AuthorDto dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setBirthdate(author.getBirthdate());
        Set<Long> books = author.getBooks().stream().map(BookEntity::getId).collect(Collectors.toSet());
        dto.setBooks(books);
        return dto;
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
