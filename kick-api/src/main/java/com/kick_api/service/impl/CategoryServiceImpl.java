package com.kick_api.service.impl;

import com.kick_api.entity.Category;
import com.kick_api.constant.ErrorCode;
import com.kick_api.exception.AppException;
import com.kick_api.repository.CategoryRepository;
import com.kick_api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Category> getActiveCategories() {
        return categoryRepository.findAllByIsActiveTrueOrderByDisplayOrderAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryBySlug(String slug) {
        return categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        category.setName(categoryDetails.getName());
        category.setSlug(categoryDetails.getSlug());
        category.setActive(categoryDetails.isActive());
        category.setDisplayOrder(categoryDetails.getDisplayOrder());
        category.setParent(categoryDetails.getParent());

        return categoryRepository.save(category);
    }
}
