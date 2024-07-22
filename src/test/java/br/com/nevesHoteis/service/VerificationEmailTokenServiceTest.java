package br.com.nevesHoteis.service;

import br.com.nevesHoteis.controller.dto.token.TokenEmailDto;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.domain.VerificationEmailToken;
import br.com.nevesHoteis.infra.exeption.EmailTokenException;
import br.com.nevesHoteis.repository.UserRepository;
import br.com.nevesHoteis.repository.VerificationEmailTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerificationEmailTokenServiceTest {
    @InjectMocks
    private VerificationEmailTokenService service;
    @Mock
    private VerificationEmailTokenRepository repository;
    @Mock
    private VerificationEmailToken verificationMock;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private User userMock;
    private final LocalDateTime toDay = LocalDateTime.now();
    @Test
    @DisplayName("Testando o reenvio do token")
    void test01() throws MessagingException, IOException {
        TokenEmailDto dto = new TokenEmailDto("artur@gmail.com", "TOKENS");
        when(repository.findByUserLogin(any())).thenReturn(Optional.of(verificationMock));
        when(verificationMock.getResendTime()).thenReturn(toDay.plusMinutes(-5));
        when(verificationMock.resendToken(any())).thenReturn(verificationMock);
        assertEquals(verificationMock, service.createdOrResend(dto));
        then(verificationMock).should().resendToken(any());
        then(emailService).should().sendConfirmEmail(any(), any());
    }
    @Test
    @DisplayName("Testando erro ao reenviar o token ")
    void test02() throws MessagingException, IOException {
        TokenEmailDto dto = new TokenEmailDto("artur@gmail.com", "TOKENS");
        when(repository.findByUserLogin(any())).thenReturn(Optional.of(verificationMock));
        when(verificationMock.getResendTime()).thenReturn(toDay.plusMinutes(5));
        assertThrows(EmailTokenException.class,()-> service.createdOrResend(dto));
        then(emailService).shouldHaveNoInteractions();
    }
    @Test
    @DisplayName("Testando a criação ou o reacriação do token")
    void test03() throws MessagingException, IOException {
        TokenEmailDto dto = new TokenEmailDto("artur@gmail.com", "TOKENS");
        when(userRepository.findByLogin(any())).thenReturn(Optional.of(userMock));
        when(repository.findByUserLogin(any())).thenReturn(Optional.empty());
        when(repository.save(any(VerificationEmailToken.class))).thenReturn(verificationMock);
        assertEquals(verificationMock, service.createdOrResend(dto));
        then(emailService).should().sendConfirmEmail(any(), any());
    }
    @Test
    @DisplayName("Testando o retorno de um erro ao tentar criar um token para um usuario inexistente")
    void test04() throws MessagingException, IOException {
        TokenEmailDto dto = new TokenEmailDto("artur@gmail.com", "TOKENS");
        when(userRepository.findByLogin(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()->  service.createdOrResend(dto));
        then(emailService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Testando a verificação correta do token")
    void test05(){
       when(repository.findByUserLogin(any())).thenReturn(Optional.of(verificationMock));
       when(verificationMock.getExpiryDate()).thenReturn(toDay.plusMinutes(5));
       when(verificationMock.getToken()).thenReturn("TOKENS");
       assertDoesNotThrow(()->service.verify(new TokenEmailDto("artur@gmail.com", "TOKENS")));
       then(verificationMock).should().activeUser();
    }
    @Test
    @DisplayName("Testando o retorno de um erro ao verificar um token que esta incorreto")
    void test06(){
        when(repository.findByUserLogin(any())).thenReturn(Optional.of(verificationMock));
        when(verificationMock.getExpiryDate()).thenReturn(toDay.plusMinutes(5));
        when(verificationMock.getToken()).thenReturn("TOKENS");
        assertThrows(EmailTokenException.class ,()->service.verify(new TokenEmailDto("artur@gmail.com", "TOKEN_INCORRETO")));
    }
    @Test
    @DisplayName("Testando o retorno de um erro quando o token esta expirado")
    void test07(){
        when(repository.findByUserLogin(any())).thenReturn(Optional.of(verificationMock));
        when(verificationMock.getExpiryDate()).thenReturn(toDay.plusMinutes(-5));
        assertThrows(EmailTokenException.class ,()->service.verify(new TokenEmailDto("artur@gmail.com", "TOKEN")));
    }
    @Test
    @DisplayName("Testando erro ao não encontrar usuario com email do token")
    void test08(){
        when(repository.findByUserLogin(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class , ()->service.verify(new TokenEmailDto("artur@gmail.com", "TOKENS")));
    }

}