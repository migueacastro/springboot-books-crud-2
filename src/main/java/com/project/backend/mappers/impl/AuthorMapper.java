package com.project.backend.mappers.impl;

import com.project.backend.domain.dto.AuthorDto;
import com.project.backend.domain.dto.BookDto;
import com.project.backend.domain.entities.AuthorEntity;
import com.project.backend.domain.entities.BookEntity;
import com.project.backend.mappers.Mapper;
import org.modelmapper.ModelMapper;

public class AuthorMapper implements Mapper<AuthorEntity, AuthorDto> {
    private ModelMapper modelMapper;

    public AuthorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
