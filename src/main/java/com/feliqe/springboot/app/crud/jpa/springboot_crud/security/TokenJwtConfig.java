package com.feliqe.springboot.app.crud.jpa.springboot_crud.security;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class TokenJwtConfig {

     //indicar cual tipo de codificacion tednra la llave del JSONWebToken (JWT)
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    //variables que se pasar al cliente
    public static final String PREFIX_TOKEN = "Bearer";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
}
