package com.feliqe.springboot.app.crud.jpa.springboot_crud.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityJsonCreator {

    @JsonCreator
    //agremamos un alias a role por authority
    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role){}
}
