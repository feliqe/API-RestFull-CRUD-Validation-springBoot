package com.feliqe.springboot.app.crud.jpa.springboot_crud.validation;

import org.springframework.util.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RequiredValidation implements ConstraintValidator<IsRequired, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        ///indicamos cual es la condicion de los campos que son requeridas
        // return (value != null && !value.isEmpty() && !value.isBlank());
        //mejor forma de validar campos String
        return StringUtils.hasText(value);
    }
}
