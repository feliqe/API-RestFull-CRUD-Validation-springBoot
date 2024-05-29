package com.feliqe.springboot.app.crud.jpa.springboot_crud.entities;

import com.feliqe.springboot.app.crud.jpa.springboot_crud.validation.IsExistsDb;
import com.feliqe.springboot.app.crud.jpa.springboot_crud.validation.IsRequired;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @IsRequired
    //metodo de la validacion por la asociacion
    @IsExistsDb
    private String sku;

    // validacion de not null
    @NotEmpty(message = "{NotEmpty.product.name}")
    @Size(min = 3, max = 50)
    private String name;

    @Min(value = 500, message = "{Min.prodcut.price}")
    @NotNull(message = "{NotNull.product.price}")
    private Integer price;

    // @NotBlank - valida que no sea null y tampoco que no tenga espacio vacios
    // @NotBlank(message = "{NotBalck.product.description}")
    // @IsRequired -  es la funcion de campos requeridos en la carpeta de valitacion
    @IsRequired
    private String description;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
}
