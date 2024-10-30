package com.alejandroflores.financialManagementAPI.service.category;

import com.alejandroflores.financialManagementAPI.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(String id);
    Category save(Category category);
    Category update(String id, Category category);
    void deleteById(String id);
}
