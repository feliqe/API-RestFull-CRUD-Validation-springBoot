package com.feliqe.springboot.app.crud.jpa.springboot_crud.services;

import java.util.List;

import com.feliqe.springboot.app.crud.jpa.springboot_crud.entities.User;
//definimos accesos a las funciones para que esten disponibles en los controladores
public interface UserService {

    List<User> findAll();

    User save(User user);

    //cargamos la funcion para validar
    boolean existsByUsername(String username);
}
