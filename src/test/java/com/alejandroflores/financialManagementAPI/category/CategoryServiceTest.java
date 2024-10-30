package com.alejandroflores.financialManagementAPI.category;
import com.alejandroflores.financialManagementAPI.model.Category;
import com.alejandroflores.financialManagementAPI.repository.category.CategoryRepository;
import com.alejandroflores.financialManagementAPI.service.category.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category();
        category.setId("1");
        category.setName("Food");
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        List<Category> result = categoryService.findAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Food", result.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        when(categoryRepository.findById("1")).thenReturn(Optional.of(category));

        // Act
        Category result = categoryService.findById("1");

        // Assert
        assertNotNull(result);
        assertEquals("Food", result.getName());
        verify(categoryRepository, times(1)).findById("1");
    }

    @Test
    void testSave() {
        // Arrange
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // Act
        Category result = categoryService.save(category);

        // Assert
        assertNotNull(result);
        assertEquals("Food", result.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testUpdate() {
        // Arrange
        when(categoryRepository.findById("1")).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // Act
        Category result = categoryService.update("1", category);

        // Assert
        assertNotNull(result);
        assertEquals("Food", result.getName());
        verify(categoryRepository, times(1)).findById("1");
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testDeleteById() {
        // Arrange
        doNothing().when(categoryRepository).deleteById("1");

        // Act
        categoryService.deleteById("1");

        // Assert
        verify(categoryRepository, times(1)).deleteById("1");
    }
}