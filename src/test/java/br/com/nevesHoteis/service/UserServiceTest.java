package br.com.nevesHoteis.service;

import br.com.nevesHoteis.controller.Dto.RedefinePasswordDto;
import br.com.nevesHoteis.controller.Dto.TokenEmailDto;
import br.com.nevesHoteis.domain.Role;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.infra.exeption.ValidateUserException;
import br.com.nevesHoteis.repository.UserRepository;
import br.com.nevesHoteis.service.validation.User.ValidateUser;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repository;
    @Mock
    private VerificationEmailTokenService verificationEmailTokenService;
    @Mock
    private User userMock;

    @Test
    @DisplayName("Testando o retorno do usuário")
    void test01(){
        when(repository.findByLogin(any())).thenReturn(Optional.of(userMock));
        assertEquals(userMock, service.loadUserByUsername("teste"));
        then(repository).should().findByLogin(any());
    }

    @Test
    @DisplayName("Testando a redefinição da senha")
    void test02(){
        when(repository.findByLogin(any())).thenReturn(Optional.of(userMock));
        doNothing().when(verificationEmailTokenService).verify(any(TokenEmailDto.class));
        RedefinePasswordDto redefinePasswordDto = new RedefinePasswordDto("artur@gmail.com", "newPassword1", "newPassword1", "Tokens");
        assertDoesNotThrow( ()->service.redefinePassword(redefinePasswordDto));
        then(userMock).should().redefinePassword(any());
    }

    @Test
    @DisplayName("Testando o retorno de um erro ao realizar a busca pelo login")
    void test03(){
        when(repository.findByLogin(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()-> service.findByLogin("teste"));
        then(repository).should().findByLogin(any());
    }
    @Test
    @DisplayName("Testando erro de senha fraca")
    void test04(){
        RedefinePasswordDto redefinePasswordDto = new RedefinePasswordDto("artur@gmail.com", "newPassword", "newPassword", "Tokens");
        assertThrows(ValidateUserException.class, ()->service.redefinePassword(redefinePasswordDto));
    }
    @Test
    @DisplayName("Testando erro de senhas diferentes")
    void test05(){
        RedefinePasswordDto redefinePasswordDto = new RedefinePasswordDto("artur@gmail.com", "newPassword1", "newPassword2", "Tokens");
        assertThrows(ValidateUserException.class, ()->service.redefinePassword(redefinePasswordDto));
    }

}