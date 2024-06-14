package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.*;
import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.domain.validation.People.ValidatePeople;
import br.com.nevesHoteis.domain.validation.User.ValidateUser;
import br.com.nevesHoteis.repository.HotelRepository;
import br.com.nevesHoteis.repository.SimpleUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimpleUserServiceTest {
    @InjectMocks
    private SimpleUserService service;
    @Mock
    private SimpleUserRepository repository;
    @Mock
    private SimpleUser tMock;
    @Mock
    private Pageable pageable;
    @Mock
    private List<ValidateUser> validateUsers;
    @Mock
    private List<ValidatePeople> validatePeople;


    @DisplayName("Testando a seleção de todos os usuarios")
    @Test
    void test01(){
        SimpleUser simpleUser= randomT();
        Page<SimpleUser> page = new PageImpl<>(List.of(simpleUser));
        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        assertEquals(page ,service.findAll(pageable));
        then(repository).should().findAll(any(Pageable.class));
    }
    @DisplayName("Testando a seleção de um usuario")
    @Test
    void test02(){
        SimpleUser simpleUser= randomT();
        when(repository.findById(anyLong())).thenReturn(Optional.of(simpleUser));
        assertEquals(simpleUser ,service.findById(simpleUser.getId()));
        then(repository).should().findById(anyLong());
    }
    @DisplayName("Testando erro ao não achar um usuario")
    @Test
    void test03(){
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> service.findById(1L));
        then(repository).should().findById(anyLong());
    }
    @DisplayName("Testando o salvamento da entidade usuario")
    @Test
    void test04(){
        SimpleUser simpleUser = randomT();
        when(repository.save(any())).thenReturn(tMock);
        assertEquals(tMock, service.save( new PeopleDto(simpleUser)));
        then(repository).should().save(any());
        then(tMock).should().passwordEncoder();
        then(validateUsers).should().forEach(any());
        then(validatePeople).should().forEach(any());

    }
    @DisplayName("Testando a atualização da entidade usuario")
    @Test
    void test05(){
        SimpleUser simpleUser = randomT();
        when(repository.findById(anyLong())).thenReturn(Optional.of(tMock));
        when(tMock.merge(any())).thenReturn(simpleUser);
        assertEquals(simpleUser, service.update(simpleUser.getId(),  new PeopleUpdateDto(simpleUser)));
        then(tMock).should().merge(any());
        then(validateUsers).should().forEach(any());
        then(validatePeople).should().forEach(any());
    }
    @DisplayName("Testando a exclusão da entidade usuario")
    @Test
    void test06(){
        SimpleUser simpleUser = randomT();
        when(repository.findById(anyLong())).thenReturn(Optional.of(tMock));
        service.delete(anyLong());
        then(repository).should().delete(any());
    }
    public SimpleUser randomT() {
        return new SimpleUser(1L, "Artur", LocalDate.now().plusYears(-18), "123.456.890-90", "73 988888888", randomAddress(), randomUser());
    }

    public Address randomAddress(){
        return new Address(1L, "76854-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
    }

    public User randomUser(){
        return new User(1L, "artur@gmail.com", "123", Role.USER);
    }
}