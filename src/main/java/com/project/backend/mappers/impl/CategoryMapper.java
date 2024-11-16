package com.project.backend.mappers.impl;

import com.project.backend.domain.dto.CategoryDto;
import com.project.backend.domain.entities.CategoryEntity;
import com.project.backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements Mapper<CategoryEntity, CategoryDto> {
    private ModelMapper modelMapper;

    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto mapTo(CategoryEntity categoryEntity) {
        return modelMapper.map(categoryEntity, CategoryDto.class);
    }

    @Override
    public CategoryEntity mapFrom(CategoryDto CategoryDto) {
        return modelMapper.map(CategoryDto, CategoryEntity.class);
    }
}
