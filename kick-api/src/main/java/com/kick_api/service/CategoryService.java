package com.kick_api.service;

import com.kick_api.entity.Category;
import java.util.List;

public interface CategoryService {
    
    List<Category> getActiveCategories();
    
    Category getCategoryBySlug(String slug);
    
    Category createCategory(Category category);
    
    Category updateCategory(Long id, Category categoryDetails);
}
