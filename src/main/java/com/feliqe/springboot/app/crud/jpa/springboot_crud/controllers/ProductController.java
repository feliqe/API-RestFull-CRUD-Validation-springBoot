package com.feliqe.springboot.app.crud.jpa.springboot_crud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feliqe.springboot.app.crud.jpa.springboot_crud.ProductValidation;
import com.feliqe.springboot.app.crud.jpa.springboot_crud.entities.Product;
import com.feliqe.springboot.app.crud.jpa.springboot_crud.services.ProductService;

import jakarta.validation.Valid;

@RestController
//definir que puedna consumir de cualquier ruta indicando con un * o por una ruta o las dos formas juntas
@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductValidation validation;

    //mostrar el listado de producto cuando la ruta del index
    @GetMapping
    @PreAuthorize("hasRole('ADMIN','USER')")
    public List<Product> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN','USER')")
    //valor ? es de un valor indefinido si es con valor o vacio
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Product> producOptional = service.findById(id);
        if (producOptional.isPresent()) {
            //validamos el campos en caso que no  exista en valos al retorno de id
            return ResponseEntity.ok(producOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    // @Valid - validamos que los campos no vayan vacios ya que la base de datos no acepta null
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result) {
        // campos de asociacion de validacion
        // validation.validate(product, result);
        if(result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));
    }

    //put acepta GET o POST
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult result, @PathVariable Long id) {
        //campos de asociacion de validacion
        validation.validate(product, result);
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Optional<Product> producOptional = service.update(id, product);
        //realiza accion en caso de existir datos o no
        if(producOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(producOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Product product = new Product();
        product.setId(id);
        Optional<Product> productOptional = service.delete(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    //funcion de mensaje de error de validation
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        //cuando se cumpla el error indicara el siguiente mensaje
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
