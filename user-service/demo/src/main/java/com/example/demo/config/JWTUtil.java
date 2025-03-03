package com.example.demo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;
    private int expiredTime = 60*100*60*1000;

    public String generateToken(String name) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("name", name)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiredTime))
                .withIssuer("TESTING 123")
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("TESTING 123")
                .build();

        System.out.println(JWT.decode(token).getExpiresAt());
        DecodedJWT jwt = verifier.verify(token);
        if(JWT.decode(token).getExpiresAt().before(new Date())){
            System.out.println("Ini new date: " + new Date());
            throw new ResourceNotFoundException("Token expired");
        }
        return jwt.getClaim("name").asString();
    }

}