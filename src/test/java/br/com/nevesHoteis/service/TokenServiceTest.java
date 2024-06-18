package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.Role;
import br.com.nevesHoteis.domain.User;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
    @InjectMocks
    private TokenService tokenService;
    private User user= new User();
    @BeforeEach
    void setUp() {
         user = new User(1L, "artur@gmail.com", "123", Role.EMPLOYEE);
    }

    @Test
    @DisplayName("Testando a criação do token")
    void test01(){
        String token = tokenService.createdToken(user);
        assertEquals(user.getLogin(), tokenService.verify(token));
    }
    @Test
    @DisplayName("Testando o lançamento da exception ")
    void test02(){
        assertThrows(JWTVerificationException.class,()-> tokenService.verify("invalid token"));
    }
    @Test
    @DisplayName("Testando o lançamento da exception ")
    void test03(){
        int num_test= 2;
        assertEquals(LocalDateTime.now().plusHours(num_test).toInstant(ZoneOffset.of("-03:00")).getEpochSecond(), tokenService.getDataExpires(num_test).getEpochSecond());
        num_test= 3;
        assertEquals(LocalDateTime.now().plusHours(num_test).toInstant(ZoneOffset.of("-03:00")).getEpochSecond(), tokenService.getDataExpires(num_test).getEpochSecond());
        assertNotEquals(LocalDateTime.now().plusHours(num_test).toInstant(ZoneOffset.of("-03:00")).getEpochSecond(), tokenService.getDataExpires(2).getEpochSecond());
    }





}