package com.alejandroflores.financialManagementAPI.usuario;

import com.alejandroflores.financialManagementAPI.model.User;
import com.alejandroflores.financialManagementAPI.repository.usuario.UserRepository;
import com.alejandroflores.financialManagementAPI.service.usuario.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UserRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear un usuario de prueba
        user = new User();
        user.setId("1");
        user.setName("John Doe");
    }

    @Test
    void testFindAll() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(user);
        when(usuarioRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = usuarioService.findAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(user));

        // Act
        User result = usuarioService.findById("1");

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(usuarioRepository, times(1)).findById("1");
    }

    @Test
    void testSave() {
        // Arrange
        when(usuarioRepository.save(any(User.class))).thenReturn(user);

        // Act
        User result = usuarioService.save(user);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(usuarioRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdate() {
        // Arrange
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(user));
        when(usuarioRepository.save(any(User.class))).thenReturn(user);

        // Act
        User result = usuarioService.update("1", user);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(usuarioRepository, times(1)).findById("1");
        verify(usuarioRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteById() {
        // Arrange
        doNothing().when(usuarioRepository).deleteById("1");

        // Act
        usuarioService.deleteById("1");

        // Assert
        verify(usuarioRepository, times(1)).deleteById("1");
    }
}