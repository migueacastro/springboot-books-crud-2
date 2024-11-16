package com.project.backend.mappers.impl;

import com.project.backend.domain.dto.BookDto;
import com.project.backend.domain.entities.BookEntity;
import com.project.backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements Mapper<BookEntity, BookDto> {

    private ModelMapper modelMapper;
    public BookMapper(ModelMapper modelMapper) { this.modelMapper = modelMapper;}

    @Override
    public BookDto mapTo(BookEntity bookEntity) { return modelMapper.map(bookEntity, BookDto.class);}

    @Override
    public BookEntity mapFrom(BookDto bookDto) { return modelMapper.map(bookDto, BookEntity.class);}
}
