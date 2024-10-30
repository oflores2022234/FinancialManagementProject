package com.alejandroflores.financialManagementAPI.income;

import com.alejandroflores.financialManagementAPI.controller.income.IncomeController;
import com.alejandroflores.financialManagementAPI.model.Income;
import com.alejandroflores.financialManagementAPI.service.income.IncomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class IncomeControllerTest {

    @Mock
    private IncomeService incomeService;

    @InjectMocks
    private IncomeController incomeController;

    private Income income;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        income = new Income();
        income.setId("1");
        income.setAmount(BigDecimal.valueOf(200.00));
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Income> incomes = Arrays.asList(income);
        when(incomeService.findAll()).thenReturn(incomes);

        // Act
        List<Income> result = incomeController.findAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals(BigDecimal.valueOf(200.00), result.get(0).getAmount());
        verify(incomeService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        when(incomeService.findById("1")).thenReturn(income);

        // Act
        Income result = incomeController.findById("1");

        // Assert
        assertEquals(BigDecimal.valueOf(200.00), result.getAmount());
        verify(incomeService, times(1)).findById("1");
    }

    @Test
    void testSave() {
        // Arrange
        when(incomeService.save(any(Income.class))).thenReturn(income);

        // Act
        Income result = incomeController.save(income);

        // Assert
        assertEquals(BigDecimal.valueOf(200.00), result.getAmount());
        verify(incomeService, times(1)).save(any(Income.class));
    }

    @Test
    void testUpdate() {
        // Arrange
        when(incomeService.update(eq("1"), any(Income.class))).thenReturn(income);

        // Act
        Income result = incomeController.update("1", income);

        // Assert
        assertEquals(BigDecimal.valueOf(200.00), result.getAmount());
        verify(incomeService, times(1)).update(eq("1"), any(Income.class));
    }

    @Test
    void testDeleteById() {
        // Act
        incomeController.deleteById("1");

        // Assert
        verify(incomeService, times(1)).deleteById("1");
    }

    @Test
    void testFindIncomesForAuthenticatedUser() {
        // Arrange
        List<Income> incomes = Arrays.asList(income);
        when(incomeService.findIncomesForAuthenticatedUser()).thenReturn(incomes);

        // Act
        List<Income> result = incomeController.findIncomesForAuthenticatedUser();

        // Assert
        assertEquals(1, result.size());
        assertEquals(BigDecimal.valueOf(200.00), result.get(0).getAmount());
        verify(incomeService, times(1)).findIncomesForAuthenticatedUser();
    }
}
