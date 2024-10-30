package com.alejandroflores.financialManagementAPI.income;


import com.alejandroflores.financialManagementAPI.model.Income;
import com.alejandroflores.financialManagementAPI.model.User;
import com.alejandroflores.financialManagementAPI.repository.income.IncomeRepository;
import com.alejandroflores.financialManagementAPI.repository.usuario.UserRepository;
import com.alejandroflores.financialManagementAPI.service.income.IncomeServiceImpl;
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

class IncomeServiceImplTest {

    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IncomeServiceImpl incomeService;

    private Income income;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("John Doe", "john@example.com", "password", "1234567890");
        user.setId("1");

        // Usando el constructor actualizado para crear un objeto Income
        income = new Income("Job", BigDecimal.valueOf(100.0), LocalDate.now(), user);
        income.setId("1");

        // Configurar el contexto de seguridad
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn("1");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testFindAll() {
        when(incomeRepository.findAll()).thenReturn(Arrays.asList(income));

        List<Income> incomes = incomeService.findAll();

        assertNotNull(incomes);
        assertEquals(1, incomes.size());
        assertEquals(BigDecimal.valueOf(100.0), incomes.get(0).getAmount());
        verify(incomeRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(incomeRepository.findById("1")).thenReturn(Optional.of(income));

        Income foundIncome = incomeService.findById("1");

        assertNotNull(foundIncome);
        assertEquals(BigDecimal.valueOf(100.0), foundIncome.getAmount());
        verify(incomeRepository, times(1)).findById("1");
    }

    @Test
    void testFindByIdNotFound() {
        when(incomeRepository.findById("1")).thenReturn(Optional.empty());

        Income foundIncome = incomeService.findById("1");

        assertNull(foundIncome);
        verify(incomeRepository, times(1)).findById("1");
    }

    @Test
    void testSave() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(incomeRepository.save(any(Income.class))).thenReturn(income);

        Income savedIncome = incomeService.save(income);

        assertNotNull(savedIncome);
        assertEquals(BigDecimal.valueOf(100.0), savedIncome.getAmount());
        assertEquals(user, savedIncome.getUser()); // Verifica que el usuario esté asignado
        verify(incomeRepository, times(1)).save(any(Income.class));
    }

    @Test
    void testUpdate() {
        Income updatedIncome = new Income("Bonus", BigDecimal.valueOf(150.0), LocalDate.now(), user);

        when(incomeRepository.findById("1")).thenReturn(Optional.of(income));
        when(incomeRepository.save(any(Income.class))).thenReturn(updatedIncome);

        Income result = incomeService.update("1", updatedIncome);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(150.0), result.getAmount());
        assertEquals("Bonus", result.getSource());
        verify(incomeRepository, times(1)).findById("1");
        verify(incomeRepository, times(1)).save(any(Income.class));
    }

    @Test
    void testUpdateIncomeNotFound() {
        Income updatedIncome = new Income("Bonus", BigDecimal.valueOf(150.0), LocalDate.now(), user);

        when(incomeRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            incomeService.update("1", updatedIncome);
        });

        assertEquals("Ingreso no encontrado", exception.getMessage());
        verify(incomeRepository, times(1)).findById("1");
    }

    @Test
    void testDeleteById() {
        doNothing().when(incomeRepository).deleteById("1");

        incomeService.deleteById("1");

        verify(incomeRepository, times(1)).deleteById("1");
    }

    @Test
    void testFindByUser() {
        when(incomeRepository.findByUser(user)).thenReturn(Arrays.asList(income));

        List<Income> incomes = incomeService.findByUser(user);

        assertNotNull(incomes);
        assertEquals(1, incomes.size());
        assertEquals(BigDecimal.valueOf(100.0), incomes.get(0).getAmount());
        verify(incomeRepository, times(1)).findByUser(user);
    }

    @Test
    void testFindIncomesForAuthenticatedUser() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(incomeRepository.findByUser(user)).thenReturn(Arrays.asList(income));

        List<Income> incomes = incomeService.findIncomesForAuthenticatedUser();

        assertNotNull(incomes);
        assertEquals(1, incomes.size());
        assertEquals(BigDecimal.valueOf(100.0), incomes.get(0).getAmount());
        verify(incomeRepository, times(1)).findByUser(user);
    }
}
