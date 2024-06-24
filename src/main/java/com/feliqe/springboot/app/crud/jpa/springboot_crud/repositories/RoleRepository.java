package com.feliqe.springboot.app.crud.jpa.springboot_crud.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.feliqe.springboot.app.crud.jpa.springboot_crud.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{

    //buscamos por el nombre
    Optional<Role> findByName(String name);
}
