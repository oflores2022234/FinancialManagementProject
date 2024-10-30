package com.alejandroflores.financialManagementAPI.controller.category;

import com.alejandroflores.financialManagementAPI.model.Category;
import com.alejandroflores.financialManagementAPI.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("/{idCategory}")
    public Category findById(@PathVariable String idCategory){
        return categoryService.findById(idCategory);
    }

    @PostMapping()
    public Category save(@RequestBody Category category){
        return categoryService.save(category);
    }

    @PutMapping("/{idCategory}")
    public Category update(@PathVariable String idCategory, @RequestBody Category category){
        return categoryService.update(idCategory, category);
    }

    @DeleteMapping("/{idCategory}")
    public void deleteById(@PathVariable String idCategory){
        categoryService.deleteById(idCategory);
    }
}
