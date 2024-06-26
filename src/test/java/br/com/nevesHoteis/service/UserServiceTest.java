package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repository;
    @Mock
    private User user;

    @Test
    @DisplayName("Testando o retorno do usuário")
    void test01(){
        when(repository.findByLogin(any())).thenReturn(Optional.of(user));
        assertEquals(user, service.loadUserByUsername("teste"));
        then(repository).should().findByLogin(any());
    }

}