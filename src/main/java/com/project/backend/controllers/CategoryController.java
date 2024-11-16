package com.project.backend.controllers;

import com.project.backend.domain.dto.CategoryDto;
import com.project.backend.domain.entities.CategoryEntity;
import com.project.backend.mappers.impl.CategoryMapper;
import com.project.backend.services.CategoryService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Log
public class CategoryController {
    private CategoryService categoryService;
    private CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }
    
    @GetMapping("/api/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategorys() {
        List<CategoryEntity> categories = categoryService.getAllCategories();
        List<CategoryDto> categoryDtoStream = categories.stream()
                .map(categoryMapper::mapTo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDtoStream);
    }


    @GetMapping("/api/categories/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        Optional<CategoryEntity> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            return ResponseEntity.ok(categoryMapper.mapTo(category.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/api/categories/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryEntity categoryDetails) {
        Optional<CategoryEntity> category = categoryService.updateCategory(id, categoryDetails);
        if (category.isPresent()) {
            return ResponseEntity.ok(categoryMapper.mapTo(category.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/api/categories")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody final CategoryDto categoryDto) {
        CategoryEntity categoryEntity = categoryMapper.mapFrom(categoryDto);
        CategoryEntity createdCategory = categoryService.createCategory(categoryEntity);
        return ResponseEntity.ok(categoryMapper.mapTo(createdCategory));
        
    }

    @DeleteMapping("/api/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (categoryService.deleteCategory(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }


}
