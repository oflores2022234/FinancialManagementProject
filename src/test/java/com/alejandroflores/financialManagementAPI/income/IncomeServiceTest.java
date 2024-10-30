package com.alejandroflores.financialManagementAPI.income;
import com.alejandroflores.financialManagementAPI.model.Income;
import com.alejandroflores.financialManagementAPI.model.User;
import com.alejandroflores.financialManagementAPI.repository.income.IncomeRepository;
import com.alejandroflores.financialManagementAPI.service.income.IncomeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class IncomeServiceTest {

    @Mock
    private IncomeRepository incomeRepository;

    @InjectMocks
    private IncomeServiceImpl incomeService;

    private Income income;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear un usuario de prueba
        user = new User();
        user.setId("1");
        user.setName("John Doe");

        // Crear un ingreso de prueba
        income = new Income();
        income.setId("1");
        income.setSource("Salary");
        income.setAmount(BigDecimal.valueOf(1000.00));
        income.setDate(LocalDate.now());
        income.setUser(user);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Income> incomes = new ArrayList<>();
        incomes.add(income);
        when(incomeRepository.findAll()).thenReturn(incomes);

        // Act
        List<Income> result = incomeService.findAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Salary", result.get(0).getSource());
        verify(incomeRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        when(incomeRepository.findById("1")).thenReturn(Optional.of(income));

        // Act
        Income result = incomeService.findById("1");

        // Assert
        assertNotNull(result);
        assertEquals("Salary", result.getSource());
        verify(incomeRepository, times(1)).findById("1");
    }

    @Test
    void testSave() {
        // Arrange
        when(incomeRepository.save(any(Income.class))).thenReturn(income);

        // Act
        Income result = incomeService.save(income);

        // Assert
        assertNotNull(result);
        assertEquals("Salary", result.getSource());
        verify(incomeRepository, times(1)).save(any(Income.class));
    }

    @Test
    void testUpdate() {
        // Arrange
        when(incomeRepository.findById("1")).thenReturn(Optional.of(income));
        when(incomeRepository.save(any(Income.class))).thenReturn(income);

        // Act
        Income result = incomeService.update("1", income);

        // Assert
        assertNotNull(result);
        assertEquals("Salary", result.getSource());
        verify(incomeRepository, times(1)).findById("1");
        verify(incomeRepository, times(1)).save(any(Income.class));
    }

    @Test
    void testDeleteById() {
        // Arrange
        doNothing().when(incomeRepository).deleteById("1");

        // Act
        incomeService.deleteById("1");

        // Assert
        verify(incomeRepository, times(1)).deleteById("1");
    }

    @Test
    void testFindByUser() {
        // Arrange
        List<Income> incomes = new ArrayList<>();
        incomes.add(income);
        when(incomeRepository.findByUser(eq(user))).thenReturn(incomes);

        // Act
        List<Income> result = incomeService.findByUser(user);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Salary", result.get(0).getSource());
        verify(incomeRepository, times(1)).findByUser(eq(user));
    }

}