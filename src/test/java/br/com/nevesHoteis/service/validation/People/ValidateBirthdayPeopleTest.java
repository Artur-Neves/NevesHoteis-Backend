package br.com.nevesHoteis.service.validation.People;

import br.com.nevesHoteis.domain.Address;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.infra.exeption.ValidateUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ValidateBirthdayPeopleTest {
    @InjectMocks
    private ValidateBirthdayPeople validate;
    private SimpleUser simpleUser;
    private LocalDate localDate = LocalDate.now();
    @BeforeEach
    void setUp() {
        simpleUser =  new SimpleUser(1L, "Artur", LocalDate.now(), "02627433024", "73 988888888", new Address(), new User());
    }

    @Test
    @DisplayName("Testando quando o usuario tem entre 14 á 130 anos")
    void test01(){
        for (int age=-14; -130 <=age; age-- ){
            simpleUser.setBirthDay(localDate.plusYears(age));
            assertDoesNotThrow(()->validate.validate(simpleUser));
        }
    }
    @Test
    @DisplayName("Testando quando o usuario não tem entre 14 á 130 anos")
    void test02(){
        for (int age=-13; 0 >=age; age++ ){
            simpleUser.setBirthDay(localDate.plusYears(age));
            assertThrows(ValidateUserException.class, ()->validate.validate(simpleUser));
        }
        for (int age=-131; -150 <=age; age-- ){
            simpleUser.setBirthDay(localDate.plusYears(age));
            assertThrows(ValidateUserException.class, ()->validate.validate(simpleUser));
        }
    }
}