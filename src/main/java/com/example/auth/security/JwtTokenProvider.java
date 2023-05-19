package com.example.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    //método para crear un token por medio de la autenticación
    public String generarToken(Authentication authentication){
        String username = authentication.getName();
        Date tiempoActual = new Date();
        Date expiracionToken = new Date(tiempoActual.getTime()+ConstantesSeguridad.JWT_EXPIRATION_TOKEN);

        //linea para generar el token
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiracionToken)
                .signWith(SignatureAlgorithm.HS512,ConstantesSeguridad.JWT_FIRMA)
                .compact();

        return token;
    }

    //Método para extraer un username a partir de un token
    public String obtenerUsernameDeJwt(String token){
        Claims claims =Jwts.parser()
                .setSigningKey(ConstantesSeguridad.JWT_FIRMA)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    //Método para validar el token
    public boolean validarToken(String token){
        try {
            Jwts.parser().setSigningKey(ConstantesSeguridad.JWT_FIRMA).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("Jwt a expirado o esta incorrecto");
        }
    }
}
