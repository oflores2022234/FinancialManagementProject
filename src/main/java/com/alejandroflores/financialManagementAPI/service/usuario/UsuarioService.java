package com.alejandroflores.financialManagementAPI.service.usuario;

import com.alejandroflores.financialManagementAPI.model.User;

import java.util.List;

public interface UsuarioService {

    List<User> findAll();
    User findById(String id);
    User save(User user);
    User update(String id, User user);
    void deleteById(String id);
}
