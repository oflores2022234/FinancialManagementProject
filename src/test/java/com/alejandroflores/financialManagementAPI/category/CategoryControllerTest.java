package com.alejandroflores.financialManagementAPI.category;

import com.alejandroflores.financialManagementAPI.model.Category;
import com.alejandroflores.financialManagementAPI.service.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.alejandroflores.financialManagementAPI.controller.category.CategoryController;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        category.setId("1");
        category.setName("Groceries");
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Category> categories = Arrays.asList(category);
        when(categoryService.findAll()).thenReturn(categories);

        // Act
        List<Category> result = categoryController.findAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Groceries", result.get(0).getName());
        verify(categoryService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        when(categoryService.findById("1")).thenReturn(category);

        // Act
        Category result = categoryController.findById("1");

        // Assert
        assertEquals("Groceries", result.getName());
        verify(categoryService, times(1)).findById("1");
    }

    @Test
    void testSave() {
        // Arrange
        when(categoryService.save(any(Category.class))).thenReturn(category);

        // Act
        Category result = categoryController.save(category);

        // Assert
        assertEquals("Groceries", result.getName());
        verify(categoryService, times(1)).save(any(Category.class));
    }

    @Test
    void testUpdate() {
        // Arrange
        when(categoryService.update(eq("1"), any(Category.class))).thenReturn(category);

        // Act
        Category result = categoryController.update("1", category);

        // Assert
        assertEquals("Groceries", result.getName());
        verify(categoryService, times(1)).update(eq("1"), any(Category.class));
    }

    @Test
    void testDeleteById() {
        // Act
        categoryController.deleteById("1");

        // Assert
        verify(categoryService, times(1)).deleteById("1");
    }
}