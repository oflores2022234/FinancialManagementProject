package com.alejandroflores.financialManagementAPI.service.category;


import com.alejandroflores.financialManagementAPI.model.Category;
import com.alejandroflores.financialManagementAPI.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(String id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category save(Category category) {

        return categoryRepository.save(category);
    }

    @Override
    public Category update(String id, Category category) {

        Category existingCategory =  categoryRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Categoría no encontrada"));

        if (category.getName() != null){
            existingCategory.setName(category.getName());
        }

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteById(String id) {
        categoryRepository.deleteById(id);
    }
}
