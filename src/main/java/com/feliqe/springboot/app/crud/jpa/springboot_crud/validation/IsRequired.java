package com.feliqe.springboot.app.crud.jpa.springboot_crud.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

//@Constraint - asociamos la condiciones requeridas indicadas en RequiredValidation
@Constraint(validatedBy = RequiredValidation.class)
//@Retention - determina el tiempo de ejecucion
@Retention(RetentionPolicy.RUNTIME)
// @Target - se puede ejecutar en mas de un tiempo
@Target({ElementType.FIELD, ElementType.METHOD})
//@interface - es una anotacion
public @interface IsRequired {
	String message() default "es requerido usando anotaciones";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
