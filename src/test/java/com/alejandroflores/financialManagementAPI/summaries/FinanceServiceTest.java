package com.alejandroflores.financialManagementAPI.summaries;

import com.alejandroflores.financialManagementAPI.model.*;
import com.alejandroflores.financialManagementAPI.model.*;
import com.alejandroflores.financialManagementAPI.repository.expense.ExpenseRepository;
import com.alejandroflores.financialManagementAPI.repository.financial.FinanceRepository;
import com.alejandroflores.financialManagementAPI.repository.income.IncomeRepository;
import com.alejandroflores.financialManagementAPI.service.financial.FinanceServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FinanceServiceTest {

    @Mock
    private FinanceRepository financialRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private IncomeRepository incomeRepository;

    @InjectMocks
    private FinanceServiceImpl financialSummaryService;

    private User user;
    private Category foodCategory;
    private Category transportCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear un usuario de prueba
        user = new User("Jane Doe", "jane@example.com", "password", "0987654321");
        user.setId("1");

        // Crear categorías de prueba
        foodCategory = new Category("Food");
        foodCategory.setId("1");
        transportCategory = new Category("Transport");
        transportCategory.setId("2");
    }

    @Test
    void testGenerateSummary() {
        // Crear gastos de prueba
        Expense expense1 = new Expense("Groceries", BigDecimal.valueOf(50.0), LocalDate.of(2024, 10, 1), foodCategory, user);
        Expense expense2 = new Expense("Bus Ticket", BigDecimal.valueOf(10.0), LocalDate.of(2024, 10, 2), transportCategory, user);
        List<Expense> expenses = Arrays.asList(expense1, expense2);

        // Crear ingresos de prueba
        Income income1 = new Income("Salary", BigDecimal.valueOf(500.0), LocalDate.of(2024, 10, 1), user);
        Income income2 = new Income("Freelance Work", BigDecimal.valueOf(200.0), LocalDate.of(2024, 10, 3), user);
        List<Income> incomes = Arrays.asList(income1, income2);

        // Configurar el comportamiento de los mocks
        when(expenseRepository.findByUserAndDateBetween(user, LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 31)))
                .thenReturn(expenses);
        when(incomeRepository.findByUserAndDateBetween(user, LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 31)))
                .thenReturn(incomes);

        // Llamar al método bajo prueba
        FinancialSummary summary = financialSummaryService.generateSummary(user, LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 31));

        // Verificar el resultado
        assertNotNull(summary);
        assertEquals(BigDecimal.valueOf(60.0), summary.getTotalExpenses());
        assertEquals(BigDecimal.valueOf(700.0), summary.getTotalIncome());
        assertEquals(BigDecimal.valueOf(640.0), summary.getBalance());

        // Verificar gastos por categoría
        Map<Category, BigDecimal> expectedExpensesByCategory = new HashMap<>();
        expectedExpensesByCategory.put(foodCategory, BigDecimal.valueOf(50.0));
        expectedExpensesByCategory.put(transportCategory, BigDecimal.valueOf(10.0));

        assertEquals(expectedExpensesByCategory, summary.getExpensesByCategory());
    }

    @Test
    void testGenerateSummaryWithNoExpensesAndIncomes() {
        // Configurar el comportamiento de los mocks para ningún ingreso o gasto
        when(expenseRepository.findByUserAndDateBetween(user, LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 31)))
                .thenReturn(Arrays.asList());
        when(incomeRepository.findByUserAndDateBetween(user, LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 31)))
                .thenReturn(Arrays.asList());

        // Llamar al método bajo prueba
        FinancialSummary summary = financialSummaryService.generateSummary(user, LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 31));

        // Verificar el resultado
        assertNotNull(summary);
        assertEquals(BigDecimal.ZERO, summary.getTotalExpenses());
        assertEquals(BigDecimal.ZERO, summary.getTotalIncome());
        assertEquals(BigDecimal.ZERO, summary.getBalance());
        assertTrue(summary.getExpensesByCategory().isEmpty());
    }
}
