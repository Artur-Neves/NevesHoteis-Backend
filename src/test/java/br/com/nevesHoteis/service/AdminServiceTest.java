package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.*;
import br.com.nevesHoteis.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


class AdminServiceTest extends PeopleServiceTest<Admin> {
    @InjectMocks
    private AdminService service;
    @Mock
    private AdminRepository repository;
    @Mock
    private Admin tMock;
    @Mock
    private User userMock;

    public AdminServiceTest() {
        super(new Admin(), Role.USER);
    }

    @DisplayName("Testando a seleção de todos os admins")
    @Test
    void test01(){
        Page<Admin> page = new PageImpl<>(List.of(t));
        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        assertEquals(page ,service.findAll(pageable));
        then(repository).should().findAll(any(Pageable.class));
    }
    @DisplayName("Testando a seleção de um admin")
    @Test
    void test02(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(t));
        assertEquals(t ,service.findById(t.getId()));
        then(repository).should().findById(anyLong());
    }
    @DisplayName("Testando erro ao não achar um admin")
    @Test
    void test03(){
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> service.findById(1L));
        then(repository).should().findById(anyLong());
    }
    @DisplayName("Testando o salvamento da entidade admin")
    @Test
    void test04(){
        when(repository.save(any())).thenReturn(tMock);
        when(tMock.getUser()).thenReturn(userMock);
        assertEquals(tMock, service.save(tMock));
        then(repository).should().save(any());
        then(userMock).should().passwordEncoder();
        then(validateUsers).should().forEach(any());
        then(validatePeoples).should().forEach(any());
    }
    @DisplayName("Testando a atualização da entidade usando o id")
    @Test
    void test05(){
        service.setValidatePeople(Arrays.asList(validateBirthdayPeople, validateCpfPeople));
        when(repository.findById(anyLong())).thenReturn(Optional.of(tMock));
        when(tMock.merge(any())).thenReturn(t);
        assertEquals(t, service.update(""+t.getId(),  t));
        then(tMock).should().merge(any());
        verify(validateBirthdayPeople).validate(t);
        verify(validateCpfPeople).validate(t);
    }
    @DisplayName("Testando a atualização da entidade usando o login")
    @Test
    void test06(){
        when(repository.findByUserLogin(any())).thenReturn(Optional.of(tMock));
        when(tMock.merge(any())).thenReturn(t);
        assertEquals(t, service.update(t.getUser().getLogin(),  t));
        then(tMock).should().merge(any());
        then(validatePeoples).should().forEach(any());
    }
    @DisplayName("Testando a exclusão da entidade admin")
    @Test
    void test07(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(tMock));
        service.delete(anyLong());
        then(repository).should().delete(any());
    }
    @Test
    @DisplayName("Testando se as validações estão sendo chamadas")
    public void testValidate() {
        People people = mock(People.class);
        User user = mock(User.class);
        when(people.getUser()).thenReturn(user);
        service.setValidatePeople(Arrays.asList(validateBirthdayPeople, validateCpfPeople));
        service.setValidateUsers(Collections.singletonList(validatePasswordUser));
        service.validate(people);
        verify(validateBirthdayPeople).validate(people);
        verify(validateCpfPeople).validate(people);
        verify(validatePasswordUser).validate(user);
    }
    @DisplayName("Testando a seleção de um admin pelo login")
    @Test
    void test08(){
        when(repository.findByUserLogin(any())).thenReturn(Optional.of(t));
        assertEquals(t ,service.findByUserLogin(t.getUser().getLogin()));
        then(repository).should().findByUserLogin(any());
    }
    @DisplayName("Testando erro ao não achar um admin pelo login")
    @Test
    void test09(){
        when(repository.findByUserLogin(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> service.findByIdOrLogin(t.getUser().getLogin()));
        then(repository).should().findByUserLogin(any());
    }
}