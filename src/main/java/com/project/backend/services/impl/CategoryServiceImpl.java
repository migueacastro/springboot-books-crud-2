package com.project.backend.services.impl;

import com.project.backend.domain.entities.CategoryEntity;
import com.project.backend.repositories.CategoryRepository;
import com.project.backend.services.CategoryService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryEntity> getAllCategories() {
        return (List<CategoryEntity>) categoryRepository.findAll();
    }

    @Override
    public CategoryEntity createCategory(CategoryEntity categoryEntity) {
        return categoryRepository.save(categoryEntity);
    }

    @Override
    public Optional<CategoryEntity> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<CategoryEntity> updateCategory(Long id, CategoryEntity categoryEntity) {
        Optional<CategoryEntity> currentCategory = getCategoryById(id);
        if (currentCategory.isPresent()) {
            CategoryEntity newCategory = currentCategory.get();
            newCategory.setName(categoryEntity.getName());
            return Optional.of(categoryRepository.save(newCategory));
        } else {
            return Optional.empty();
        }

    }

    @Override
    public Boolean deleteCategory(Long id) {
        if (categoryRepository.findById(id).isPresent()) {
            categoryRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
