package com.alejandroflores.financialManagementAPI.category;

import com.alejandroflores.financialManagementAPI.model.Category;
import com.alejandroflores.financialManagementAPI.repository.category.CategoryRepository;
import com.alejandroflores.financialManagementAPI.service.category.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category("Food");
        category.setId("1");
    }

    @Test
    void testFindAll() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));

        List<Category> categories = categoryService.findAll();

        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals("Food", categories.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(categoryRepository.findById("1")).thenReturn(Optional.of(category));

        Category foundCategory = categoryService.findById("1");

        assertNotNull(foundCategory);
        assertEquals("Food", foundCategory.getName());
        verify(categoryRepository, times(1)).findById("1");
    }

    @Test
    void testFindByIdNotFound() {
        when(categoryRepository.findById("1")).thenReturn(Optional.empty());

        Category foundCategory = categoryService.findById("1");

        assertNull(foundCategory);
        verify(categoryRepository, times(1)).findById("1");
    }

    @Test
    void testSave() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category savedCategory = categoryService.save(category);

        assertNotNull(savedCategory);
        assertEquals("Food", savedCategory.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testUpdate() {
        Category updatedCategory = new Category("Beverages");
        updatedCategory.setId("1");

        when(categoryRepository.findById("1")).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        Category result = categoryService.update("1", updatedCategory);

        assertNotNull(result);
        assertEquals("Beverages", result.getName());
        verify(categoryRepository, times(1)).findById("1");
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testUpdateCategoryNotFound() {
        Category updatedCategory = new Category("Beverages");
        updatedCategory.setId("1");

        when(categoryRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.update("1", updatedCategory);
        });

        assertEquals("Categoría no encontrada", exception.getMessage());
        verify(categoryRepository, times(1)).findById("1");
    }

    @Test
    void testDeleteById() {
        doNothing().when(categoryRepository).deleteById("1");

        categoryService.deleteById("1");

        verify(categoryRepository, times(1)).deleteById("1");
    }
}