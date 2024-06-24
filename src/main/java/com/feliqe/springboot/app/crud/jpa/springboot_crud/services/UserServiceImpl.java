package com.feliqe.springboot.app.crud.jpa.springboot_crud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feliqe.springboot.app.crud.jpa.springboot_crud.entities.Role;
import com.feliqe.springboot.app.crud.jpa.springboot_crud.entities.User;
import com.feliqe.springboot.app.crud.jpa.springboot_crud.repositories.RoleRepository;
import com.feliqe.springboot.app.crud.jpa.springboot_crud.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {

        //indicamos el campo de la base de datos
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();

        //add - realizamos la carga si existe
        optionalRoleUser.ifPresent(roles::add);

        //validar si el rol es administrador
        if (user.isAdmin()) {
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }

        user.setRoles(roles);
        //password encriptado
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    //funcion de validacion
    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

}
