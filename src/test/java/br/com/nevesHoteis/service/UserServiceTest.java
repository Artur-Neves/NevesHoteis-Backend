package br.com.nevesHoteis.service;

import br.com.nevesHoteis.controller.dto.user.RedefinePasswordDto;
import br.com.nevesHoteis.controller.dto.token.TokenEmailDto;
import br.com.nevesHoteis.domain.*;
import br.com.nevesHoteis.infra.exeption.ValidateUserException;
import br.com.nevesHoteis.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static br.com.nevesHoteis.domain.Role.ADMIN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repository;
    @Mock
    private SimpleUserService simpleUserRepository;
    @Mock
    private EmployeeService employeeRepository;
    @Mock
    private AdminService adminRepository;
    @Mock
    private VerificationEmailTokenService verificationEmailTokenService;
    @Mock
    private User userMock;
    @Mock
    private Authentication authentication;
    @Mock
    private UserDetails userDetailsMock;
    @Mock
    private SimpleUser simpleUser;
    @Mock
    private Employee employee;
    @Mock
    private Admin admin;

    @Mock
    private SecurityContext securityContext;


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
    @Test
    @DisplayName("Testando a busca dos dados de um userSimples pelo login")
    void test06(){
        SecurityContextHolder.setContext(securityContext);
        Authentication authenticationUser = new UsernamePasswordAuthenticationToken("teste@gmal.com", "", List.of(Role.USER));
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authenticationUser);
        when(simpleUserRepository.findByUserLogin(any())).thenReturn(simpleUser);
        assertEquals(simpleUser,  service.findPeopleByLogin());
    }
    @Test
    @DisplayName("Testando a busca dos dados de um funcionário pelo login")
    void test07(){
        SecurityContextHolder.setContext(securityContext);
        Authentication authenticationEmployee = new UsernamePasswordAuthenticationToken("teste@gmal.com", "", List.of(Role.EMPLOYEE));
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authenticationEmployee);
        when(employeeRepository.findByUserLogin(any())).thenReturn(employee);
        assertEquals(employee,  service.findPeopleByLogin());
    }
    @Test
    @DisplayName("Testando a busca dos dados de um admin pelo login")
    void test08(){
        SecurityContextHolder.setContext(securityContext);
        Authentication authenticationAdmin = new UsernamePasswordAuthenticationToken("teste@gmal.com", "", List.of(ADMIN));
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authenticationAdmin);
        when(adminRepository.findByUserLogin(any())).thenReturn(admin);
        assertEquals(admin,  service.findPeopleByLogin());
    }
    @Test
    @DisplayName("Testando retorno de um erro ao não passar um perfil")
    void test09(){
        SecurityContextHolder.setContext(securityContext);
        Authentication authenticationAdmin = new UsernamePasswordAuthenticationToken("teste@gmal.com", "", List.of());
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authenticationAdmin);

        assertThrows(ValidateUserException.class, ()->  service.findPeopleByLogin());
    }

@Test
@DisplayName("Testando a atualização de um userSimples pelo login")
void test10(){
    SecurityContextHolder.setContext(securityContext);
    Authentication authenticationUser = new UsernamePasswordAuthenticationToken("teste@gmal.com", "", List.of(Role.USER));
    when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authenticationUser);
    when(simpleUserRepository.update(any(), any())).thenReturn(simpleUser);
    assertEquals(simpleUser,  service.updateMyAccount(simpleUser));
}
    @Test
    @DisplayName("Testando a atualização dos dados de um funcionário pelo login")
    void test11(){
        SecurityContextHolder.setContext(securityContext);
        Authentication authenticationEmployee = new UsernamePasswordAuthenticationToken("teste@gmal.com", "", List.of(Role.EMPLOYEE));
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authenticationEmployee);
        when(employeeRepository.update(any(), any())).thenReturn(employee);
        assertEquals(employee,  service.updateMyAccount(employee));
    }
    @Test
    @DisplayName("Testando a atualização dos dados de um admin pelo login")
    void test12(){
        SecurityContextHolder.setContext(securityContext);
        Authentication authenticationAdmin = new UsernamePasswordAuthenticationToken("teste@gmal.com", "", List.of(ADMIN));
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authenticationAdmin);
        when(adminRepository.update(any(), any())).thenReturn(admin);
        assertEquals(admin,  service.updateMyAccount(admin));
    }

}