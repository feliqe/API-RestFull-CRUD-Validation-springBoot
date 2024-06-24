package com.feliqe.springboot.app.crud.jpa.springboot_crud.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feliqe.springboot.app.crud.jpa.springboot_crud.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//impostamos la clase de TokenJwtConfig
import static com.feliqe.springboot.app.crud.jpa.springboot_crud.security.TokenJwtConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // se crea este metodo realizando accion de codigo fuente -> overrider .. -> se buscar por la palabra clave "att" y se carga como el nombre del metodo
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        User user = null;
        String username = null;
        String password = null;

        try {
            // alamcenamos los campos del JSON request y los alamacenamos en la variable user
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //pasamos por la autenticacion
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);

                //retornamos los campos del token
        return authenticationManager.authenticate(authenticationToken);
    }

    //se crea este metodo realizando accion de codigo fuente -> overrider .. -> se buscar por la palabra clave "su" y se carga como el nombre del metodo
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        String username = user.getUsername();
        //indicamos cualquier autorizacion que tenga de los roles
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        //pasar el rol a la configuracion del TOKEN se realiza con Claims
        Claims claims = Jwts.claims()
        //indicamos los campos para poder cargra la informacion del token
        .add("authorities", new ObjectMapper().writeValueAsString(roles))
        .add("username", username)
        .build();

        //indicamos los datos del TOKEN
        String token = Jwts.builder()
                .subject(username)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000)) //indicamos la fecha actual con los milisegundos de una hora
                .issuedAt(new Date()) //la fecha de creacion del token
                .signWith(SECRET_KEY)
                .compact();

        //se lo pasamos al cliente
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        //pasamos los campos he indicamos cuando esta ok la session
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username);
        body.put("message", String.format("Hola %s has iniciado session con exito!", username));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(200);
    }

    //cuando hay un erro en la autenticacion
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

        Map<String, String> body = new HashMap<>();
        body.put("message", "Error en la autenticacion de username, o password incorrectos!");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(CONTENT_TYPE);
    }
}
