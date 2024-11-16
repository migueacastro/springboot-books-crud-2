package com.project.backend.services;

import com.project.backend.domain.entities.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryEntity createCategory(CategoryEntity categoryEntity);

    Optional<CategoryEntity> getCategoryById(Long id);
    Optional<CategoryEntity> updateCategory(Long id, CategoryEntity categoryEntity);
    List<CategoryEntity> getAllCategories();
    Boolean deleteCategory(Long id);
}
