package com.alejandroflores.financialManagementAPI.usuario;

import com.alejandroflores.financialManagementAPI.controller.usuario.UserController;
import com.alejandroflores.financialManagementAPI.model.User;
import com.alejandroflores.financialManagementAPI.service.usuario.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        user = new User();
        user.setId("1");
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
    }

    @Test
    void testFindAll() {
        // Arrange
        List<User> users = Arrays.asList(user);
        when(usuarioService.findAll()).thenReturn(users);

        // Act
        List<User> result = userController.findAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(usuarioService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        when(usuarioService.findById("1")).thenReturn(user);

        // Act
        User result = userController.findById("1");

        // Assert
        assertEquals("John Doe", result.getName());
        verify(usuarioService, times(1)).findById("1");
    }

    @Test
    void testSave() {
        // Arrange
        when(usuarioService.save(any(User.class))).thenReturn(user);

        // Act
        User result = userController.save(user);

        // Assert
        assertEquals("John Doe", result.getName());
        verify(usuarioService, times(1)).save(any(User.class));
    }

    @Test
    void testUpdate() {
        // Arrange
        when(usuarioService.update(eq("1"), any(User.class))).thenReturn(user);

        // Act
        User result = userController.update("1", user);

        // Assert
        assertEquals("John Doe", result.getName());
        verify(usuarioService, times(1)).update(eq("1"), any(User.class));
    }

    @Test
    void testDeleteById() {
        // Act
        userController.deleteById("1");

        // Assert
        verify(usuarioService, times(1)).deleteById("1");
    }
}
