package com.feliqe.springboot.app.crud.jpa.springboot_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feliqe.springboot.app.crud.jpa.springboot_crud.entities.Product;
import com.feliqe.springboot.app.crud.jpa.springboot_crud.repositories.ProductRepository;

//logita de negocio
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Optional<Product> update(Long id, Product product) {
        Optional<Product> producOptional = repository.findById(id);
        if(producOptional.isPresent()){
            Product productDb = producOptional.orElseThrow();
            //asignamos los datos de entrada para poder realizar la actualizacion con los datos antiguos
            productDb.setSku(product.getSku());
            productDb.setName(product.getName());
            productDb.setDescription(product.getDescription());
            productDb.setPrice(product.getPrice());
            return Optional.of(repository.save(productDb));
        }
        return producOptional;
    }

    @Override
    @Transactional
    public Optional<Product> delete(Long id) {
        //buscamos el id en la base de datos
        Optional<Product> producOptional = repository.findById(id);
        producOptional.ifPresent(productDb -> {
            repository.delete(productDb);
        });
        return producOptional;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsBySku(String sku) {
        return repository.existsBySku(sku);
    }
}
