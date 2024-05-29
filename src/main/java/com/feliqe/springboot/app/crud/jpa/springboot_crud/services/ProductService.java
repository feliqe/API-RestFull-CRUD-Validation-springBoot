package com.feliqe.springboot.app.crud.jpa.springboot_crud.services;

import java.util.List;
import java.util.Optional;

import com.feliqe.springboot.app.crud.jpa.springboot_crud.entities.Product;

public interface ProductService {

    //metod para listar
    List<Product> findAll();

    //metodo para buscar por id
    Optional<Product> findById(Long id);

    //metodo para guardar
    Product save(Product product);

    Optional<Product> update(Long id, Product product);

    //metodo para borrar
    Optional<Product> delete(Long id);

    // metodo para revisar si existe sku
    boolean existsBySku(String sku);
}
