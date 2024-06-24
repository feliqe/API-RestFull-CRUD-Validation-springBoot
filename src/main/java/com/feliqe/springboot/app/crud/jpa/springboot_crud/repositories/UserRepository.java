package com.feliqe.springboot.app.crud.jpa.springboot_crud.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.feliqe.springboot.app.crud.jpa.springboot_crud.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
    //se crea el metodo para validar los campos
    boolean existsByUsername(String username);

    //buscar por el nombre de usuario
    Optional<User> findByUsername(String username);
}
