package com.feliqe.springboot.app.crud.jpa.springboot_crud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.feliqe.springboot.app.crud.jpa.springboot_crud.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{
    //metodo para revisar si existe sku
    boolean existsBySku(String sku);
}
