package com.project.backend.mappers.impl;

import com.project.backend.domain.dto.BookDto;
import com.project.backend.domain.entities.AuthorEntity;
import com.project.backend.domain.entities.BookEntity;
import com.project.backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookMapper implements Mapper<BookEntity, BookDto> {

    private ModelMapper modelMapper;
    public BookMapper(ModelMapper modelMapper) { this.modelMapper = modelMapper;}

    @Override
    public BookDto mapTo(BookEntity book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setDescription(book.getDescription());
        CategoryMapper categoryMapper = new CategoryMapper(modelMapper);
        dto.setCategory(categoryMapper.mapTo(book.getCategory()));
        Set<Long> authors = book.getAuthors().stream() .map(AuthorEntity::getId) .collect(Collectors.toSet());
        dto.setAuthors(authors);
        return dto;
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) { return modelMapper.map(bookDto, BookEntity.class);}
}
