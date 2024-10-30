package com.alejandroflores.financialManagementAPI.expense;
import com.alejandroflores.financialManagementAPI.model.Expense;
import com.alejandroflores.financialManagementAPI.model.User;
import com.alejandroflores.financialManagementAPI.repository.expense.ExpenseRepository;
import com.alejandroflores.financialManagementAPI.service.expense.ExpenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private Expense expense;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear un gasto de ejemplo
        expense = new Expense();
        expense.setId("1");
        expense.setAmount(BigDecimal.valueOf(100.00)); // Usar BigDecimal
        expense.setDescription("Groceries");

        user = new User();
        user.setId("1");
        user.setName("John Doe");
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Expense> expenses = new ArrayList<>();
        expenses.add(expense);
        when(expenseRepository.findAll()).thenReturn(expenses);

        // Act
        List<Expense> result = expenseService.findAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Groceries", result.get(0).getDescription());
        assertEquals(BigDecimal.valueOf(100.00), result.get(0).getAmount()); // Verificar el monto
        verify(expenseRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        when(expenseRepository.findById("1")).thenReturn(Optional.of(expense));

        // Act
        Expense result = expenseService.findById("1");

        // Assert
        assertNotNull(result);
        assertEquals("Groceries", result.getDescription());
        assertEquals(BigDecimal.valueOf(100.00), result.getAmount()); // Verificar el monto
        verify(expenseRepository, times(1)).findById("1");
    }


    @Test
    void testUpdate() {
        // Arrange
        when(expenseRepository.findById("1")).thenReturn(Optional.of(expense));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        // Act
        Expense result = expenseService.update("1", expense);

        // Assert
        assertNotNull(result);
        assertEquals("Groceries", result.getDescription());
        assertEquals(BigDecimal.valueOf(100.00), result.getAmount()); // Verificar el monto
        verify(expenseRepository, times(1)).findById("1");
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void testDeleteById() {
        // Arrange
        doNothing().when(expenseRepository).deleteById("1");

        // Act
        expenseService.deleteById("1");

        // Assert
        verify(expenseRepository, times(1)).deleteById("1");
    }

    @Test
    void testFindByUser() {
        // Arrange
        List<Expense> expenses = new ArrayList<>();
        expenses.add(expense);
        when(expenseRepository.findByUser(eq(user))).thenReturn(expenses);

        // Act
        List<Expense> result = expenseService.findByUser(user);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Groceries", result.get(0).getDescription());
        assertEquals(BigDecimal.valueOf(100.00), result.get(0).getAmount()); // Verificar el monto
        verify(expenseRepository, times(1)).findByUser(eq(user));
    }

}