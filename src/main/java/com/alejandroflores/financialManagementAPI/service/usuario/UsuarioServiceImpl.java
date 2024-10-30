package com.alejandroflores.financialManagementAPI.service.usuario;

import com.alejandroflores.financialManagementAPI.model.User;
import com.alejandroflores.financialManagementAPI.repository.usuario.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {


    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(String id, User user) {
        User existingUser = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Usuario no encontrado"));

        if (user.getName() != null){
            existingUser.setName(user.getName());
        }
        if (user.getEmail() != null){
            existingUser.setEmail(user.getEmail());
        }
        if (user.getPhone() != null){
            existingUser.setPhone(user.getPhone());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}
