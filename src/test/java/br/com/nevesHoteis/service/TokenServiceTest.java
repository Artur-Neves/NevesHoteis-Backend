package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.Role;
import br.com.nevesHoteis.domain.User;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
    @InjectMocks
    private TokenService tokenService;
    @Mock
    Algorithm algorithm;
    private User user= new User();
    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenService, "secretAlgorithm", "Testing");
        ReflectionTestUtils.setField(tokenService, "secretRefreshAlgorithm", "TestingRefresh");
        user = new User(1L, "artur@gmail.com",true, "123", Role.EMPLOYEE, null);
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

    @Test
    @DisplayName("Testando a criação do token mas com a assinatura diferente")
    void test04(){
        String token = tokenService.createdToken(user.getLogin(), user.getRole().name());
        assertEquals(user.getLogin(), tokenService.verify(token));
    }
    @Test
    @DisplayName("Testando a criação do refreshtoken ")
    void test05(){
        String token = tokenService.createdRefreshToken(user);
        assertDoesNotThrow(()-> tokenService.verifyRefresh(token));
    }
    @Test
    @DisplayName("Testando a restauração do token com o refreshToken")
    void test06(){
        String token = tokenService.createdRefreshToken(user);
        assertDoesNotThrow(()->tokenService.verify(tokenService.refreshToken(token)));
    }
    @Test
    @DisplayName("Testando o retorno de uma exception ao tentar passar um refresh token incorreto")
    void test07(){
        String token = tokenService.createdToken(user);
        assertThrows(JWTVerificationException.class,()->tokenService.refreshToken(token));
    }
    @Test
    @DisplayName("Testando o envio correto dos dois tokens após o login")
    void test08(){
        Map<String, String> map = tokenService.tokensAfterLoginToken(user);
       assertDoesNotThrow(()->tokenService.verify(map.get("token")));
        assertDoesNotThrow(()->tokenService.verifyRefresh(map.get("refresh")));

    }
    @Test
    @DisplayName("Testando o retorno de uma exception ao tentar verificar um token com um método para o refresh token e vice-versa")
    void test09(){
        String token = tokenService.createdToken(user);
        String refreshToken = tokenService.createdRefreshToken(user);
        assertThrows(JWTVerificationException.class,()->tokenService.verify(refreshToken));
        assertThrows(JWTVerificationException.class,()->tokenService.verifyRefresh(token));
    }






}