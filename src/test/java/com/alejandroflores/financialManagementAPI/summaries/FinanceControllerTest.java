package com.alejandroflores.financialManagementAPI.summaries;

import com.alejandroflores.financialManagementAPI.controller.financial.FinanceController;
import com.alejandroflores.financialManagementAPI.model.FinancialSummary;
import com.alejandroflores.financialManagementAPI.model.User;
import com.alejandroflores.financialManagementAPI.service.financial.FinanceService;
import com.alejandroflores.financialManagementAPI.service.usuario.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class FinanceControllerTest {

    @Mock
    private FinanceService financeService;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private FinanceController financeController;

    private User user;
    private FinancialSummary financialSummary;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        user = new User();
        user.setId("1");
        user.setName("John Doe");

        financialSummary = new FinancialSummary();
        financialSummary.setTotalIncome(new BigDecimal("1000.00"));
        financialSummary.setTotalExpenses(new BigDecimal("500.00"));
        financialSummary.setBalance(new BigDecimal("500.00"));
    }

    @Test
    void testGetFinancialSummary() {
        // Arrange
        LocalDate from = LocalDate.of(2023, 1, 1);
        LocalDate to = LocalDate.of(2023, 12, 31);

        when(usuarioService.findById("1")).thenReturn(user);
        when(financeService.generateSummary(any(User.class), eq(from), eq(to)))
                .thenReturn(financialSummary);

        // Act
        ResponseEntity<FinancialSummary> response = financeController.getFinancialSummary("1", from, to);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(new BigDecimal("1000.00"), response.getBody().getTotalIncome());
        assertEquals(new BigDecimal("500.00"), response.getBody().getTotalExpenses());
        assertEquals(new BigDecimal("500.00"), response.getBody().getBalance());
        verify(usuarioService, times(1)).findById("1");
        verify(financeService, times(1)).generateSummary(any(User.class), eq(from), eq(to));
    }
}