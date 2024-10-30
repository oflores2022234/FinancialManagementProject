package com.alejandroflores.financialManagementAPI.auth;

import com.alejandroflores.financialManagementAPI.controller.auth.AuthController;
import com.alejandroflores.financialManagementAPI.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private Map<String, String> credentials;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("password", "password123");
    }

    @Test
    void testLogin_Success() {
        // Arrange
        String token = "mockToken";
        when(authService.login("test@example.com", "password123")).thenReturn(token);

        // Act
        String result = authController.login(credentials);

        // Assert
        assertEquals(token, result);
        verify(authService, times(1)).login("test@example.com", "password123");
    }

    @Test
    void testLogin_InvalidCredentials() {
        // Arrange
        when(authService.login("test@example.com", "wrongPassword"))
                .thenThrow(new RuntimeException("Invalid credentials"));
        credentials.put("password", "wrongPassword");

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authController.login(credentials);
        });

        assertEquals("Invalid credentials", exception.getMessage());
        verify(authService, times(1)).login("test@example.com", "wrongPassword");
    }

}