package com.alejandroflores.financialManagementAPI.expense;

import com.alejandroflores.financialManagementAPI.model.Expense;
import com.alejandroflores.financialManagementAPI.model.Category;
import com.alejandroflores.financialManagementAPI.model.User;
import com.alejandroflores.financialManagementAPI.repository.expense.ExpenseRepository;
import com.alejandroflores.financialManagementAPI.repository.usuario.UserRepository;
import com.alejandroflores.financialManagementAPI.service.expense.ExpenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private Expense expense;
    private User user;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear un usuario de prueba
        user = new User("John Doe", "john@example.com", "password", "1234567890");
        user.setId("1");

        // Crear una categoría de prueba
        category = new Category("Food");
        category.setId("2");

        // Crear un gasto de prueba utilizando el constructor actualizado
        expense = new Expense("Dinner", BigDecimal.valueOf(50.0), LocalDate.now(), category, user);
        expense.setId("1");

        // Configurar el contexto de seguridad
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn("1");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testFindAll() {
        when(expenseRepository.findAll()).thenReturn(Arrays.asList(expense));

        List<Expense> expenses = expenseService.findAll();

        assertNotNull(expenses);
        assertEquals(1, expenses.size());
        assertEquals(BigDecimal.valueOf(50.0), expenses.get(0).getAmount());
        verify(expenseRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(expenseRepository.findById("1")).thenReturn(Optional.of(expense));

        Expense foundExpense = expenseService.findById("1");

        assertNotNull(foundExpense);
        assertEquals(BigDecimal.valueOf(50.0), foundExpense.getAmount());
        verify(expenseRepository, times(1)).findById("1");
    }

    @Test
    void testFindByIdNotFound() {
        when(expenseRepository.findById("1")).thenReturn(Optional.empty());

        Expense foundExpense = expenseService.findById("1");

        assertNull(foundExpense);
        verify(expenseRepository, times(1)).findById("1");
    }

    @Test
    void testSave() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        Expense savedExpense = expenseService.save(expense);

        assertNotNull(savedExpense);
        assertEquals(BigDecimal.valueOf(50.0), savedExpense.getAmount());
        assertEquals(user, savedExpense.getUser());
        assertEquals(category, savedExpense.getCategory());
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void testUpdate() {
        Expense updatedExpense = new Expense("Grocery", BigDecimal.valueOf(70.0), LocalDate.now(), category, user);

        when(expenseRepository.findById("1")).thenReturn(Optional.of(expense));
        when(expenseRepository.save(any(Expense.class))).thenReturn(updatedExpense);

        Expense result = expenseService.update("1", updatedExpense);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(70.0), result.getAmount());
        assertEquals("Grocery", result.getDescription());
        assertEquals(category, result.getCategory());
        verify(expenseRepository, times(1)).findById("1");
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void testUpdateExpenseNotFound() {
        Expense updatedExpense = new Expense("Grocery", BigDecimal.valueOf(70.0), LocalDate.now(), category, user);

        when(expenseRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            expenseService.update("1", updatedExpense);
        });

        assertEquals("Gasto no encontrado", exception.getMessage());
        verify(expenseRepository, times(1)).findById("1");
    }

    @Test
    void testDeleteById() {
        doNothing().when(expenseRepository).deleteById("1");

        expenseService.deleteById("1");

        verify(expenseRepository, times(1)).deleteById("1");
    }

    @Test
    void testFindByUser() {
        when(expenseRepository.findByUser(user)).thenReturn(Arrays.asList(expense));

        List<Expense> expenses = expenseService.findByUser(user);

        assertNotNull(expenses);
        assertEquals(1, expenses.size());
        assertEquals(BigDecimal.valueOf(50.0), expenses.get(0).getAmount());
        verify(expenseRepository, times(1)).findByUser(user);
    }

    @Test
    void testFindIncomesForAuthenticatedUser() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(expenseRepository.findByUser(user)).thenReturn(Arrays.asList(expense));

        List<Expense> expenses = expenseService.findIncomesForAuthenticatedUser();

        assertNotNull(expenses);
        assertEquals(1, expenses.size());
        assertEquals(BigDecimal.valueOf(50.0), expenses.get(0).getAmount());
        verify(expenseRepository, times(1)).findByUser(user);
    }
}