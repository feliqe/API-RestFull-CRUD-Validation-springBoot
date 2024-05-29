package com.feliqe.springboot.app.crud.jpa.springboot_crud.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feliqe.springboot.app.crud.jpa.springboot_crud.services.ProductService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class IsExistsDbValidation implements ConstraintValidator<IsExistsDb, String>{
    //importamos la clase de ProdcutService
    @Autowired
    private ProductService service;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //realizamo validacion de campos a consulta y el de la clase con el metodo creado
        return !service.existsBySku(value);
    }

}
