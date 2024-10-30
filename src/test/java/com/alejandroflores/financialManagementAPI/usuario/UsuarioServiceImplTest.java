package com.alejandroflores.financialManagementAPI.usuario;

import com.alejandroflores.financialManagementAPI.model.User;
import com.alejandroflores.financialManagementAPI.repository.usuario.UserRepository;
import com.alejandroflores.financialManagementAPI.service.usuario.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("John Doe", "john@example.com", "franco123", "1234567890");
    }

    @Test
    void testFindAll() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = usuarioService.findAll();

        System.out.println("User name: " + users.get(0).getName());

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(userRepository.findById("2")).thenReturn(Optional.of(user));

        User foundUser = usuarioService.findById("2");

        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
        verify(userRepository, times(1)).findById("2");
    }

    @Test
    void testSave() {
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = usuarioService.save(user);

        assertNotNull(savedUser);
        assertEquals("John Doe", savedUser.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdate() {
        User updatedUser = new User( "Jane Doe", "jane@example.com", "franco123", "0987654321");
        when(userRepository.findById("2")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = usuarioService.update("2", updatedUser);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        verify(userRepository, times(1)).findById("2");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUserNotFound() {
        User updatedUser = new User("Jane Doe", "jane@example.com", "franco123", "0987654321");
        when(userRepository.findById("2")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.update("2", updatedUser);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepository, times(1)).findById("2");
    }

    @Test
    void testDeleteById() {
        doNothing().when(userRepository).deleteById("2");

        usuarioService.deleteById("2");

        verify(userRepository, times(1)).deleteById("2");
    }
}