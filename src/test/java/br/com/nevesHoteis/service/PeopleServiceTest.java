package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.*;
import br.com.nevesHoteis.service.validation.People.ValidateBirthdayPeople;
import br.com.nevesHoteis.service.validation.People.ValidateCpfPeople;
import br.com.nevesHoteis.service.validation.People.ValidatePeople;
import br.com.nevesHoteis.service.validation.User.ValidatePasswordUser;
import br.com.nevesHoteis.service.validation.User.ValidateUser;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest <T extends People> {


    @Mock
    protected Pageable pageable;
    @Mock
    protected List<ValidateUser> validateUsers;
    @Mock
    protected List<ValidatePeople> validatePeoples;
    @Mock
    protected ValidateCpfPeople validateCpfPeople;
    @Mock
    protected ValidateBirthdayPeople validateBirthdayPeople;
    @Mock
    protected ValidatePasswordUser validatePasswordUser;

    protected T t;
    protected Address address = new Address();
    protected User user = new User();

    public PeopleServiceTest(T t, Role role) {
        this.user = new User(1L, "artur@gmail.com", true,"123", role, null);
        this.address = new Address( "76854-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
        this.t = t;
        t.setId(1L);
        t.setName("Artur");
        t.setBirthDay(LocalDate.now().plusYears(-18));
        t.setCpf("123.456.890-90");
        t.setPhone("73 988888888");
        t.setAddress(address);
        t.setUser(user);

    }



}
