package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    Algorithm algorithm = Algorithm.HMAC256("Testing");
    Algorithm algorithmRefresh = Algorithm.HMAC256("Testing");
    final int TIME_TOKEN = 2;
    final int TIME_REFRESH_TOKEN = 30 * 24;
    public String createdToken(User user){
            return JWT.create()
                    .withIssuer("Neves Hoteis")
                    .withSubject(user.getLogin())
                    .withClaim("role", user.getRole().name())
                    .withExpiresAt(getDataExpires(TIME_TOKEN))
                    .sign(algorithm);
    }
    public String createdToken(String login, String role){
        return JWT.create()
                .withIssuer("Neves Hoteis")
                .withSubject(login)
                .withClaim("role", role)
                .withExpiresAt(getDataExpires(TIME_TOKEN))
                .sign(algorithm);
    }
    public String refreshToken(String refreshToken){
        verifyRefresh(refreshToken);
        DecodedJWT user = JWT.decode(refreshToken);
        return createdToken(user.getSubject(), user.getClaim("role").asString());
    }
    public String createdRefreshToken(User user){
        return JWT.create()
                .withIssuer("Neves Hoteis")
                .withSubject(user.getLogin())
                .withClaim("role", user.getRole().name())
                .withExpiresAt(getDataExpires(TIME_REFRESH_TOKEN))
                .sign(algorithmRefresh);
    }
    protected Instant getDataExpires(int hoursValid) {
        return LocalDateTime.now().plusHours(hoursValid).toInstant(ZoneOffset.of("-03:00"));
    }
    public String verify(String token){
        try {
           return JWT.require(algorithm)
                    .withIssuer("Neves Hoteis")
                    .build()
                   .verify(token)
                   .getSubject();
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token expirado ou inválido");
        }
    }
    public void verifyRefresh(String token){
        try {
             JWT.require(algorithmRefresh)
                    .withIssuer("Neves Hoteis")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token expirado ou inválido");
        }
    }
    public Map<String, String> tokensAfterLoginToken(User user){
        String token = createdToken(user);
        String refreshToken = createdRefreshToken(user);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("refresh", refreshToken);
        return map;
    }
}
