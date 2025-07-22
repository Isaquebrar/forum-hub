package com.forumhub.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.forumhub.domain.usuario.Usuario;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Service
public class TokenService {

    @Value("${forumhub.jwt.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        SecretKey chave = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuer("Forum Hub API")
                .setIssuedAt(new Date())
                .setExpiration(Date.from(tempoExpiracao()))
                .signWith(chave, SignatureAlgorithm.HS256)
                .compact();
    }

    private Instant tempoExpiracao() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00")); // Horário de Brasília
    }

    public String getSubject(String tokenJWT) {
        SecretKey chave = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.parserBuilder()
                .setSigningKey(chave)
                .build()
                .parseClaimsJws(tokenJWT)
                .getBody()
                .getSubject();
    }
}
