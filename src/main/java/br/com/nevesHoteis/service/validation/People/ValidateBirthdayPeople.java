package br.com.nevesHoteis.service.validation.People;

import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.infra.exeption.ValidateUserException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class ValidateBirthdayPeople implements ValidatePeople{
    @Override
    public void validate(People people) {
    if (!(people.getBirthDay().isBefore(LocalDate.now().plusYears(-13)) && people.getBirthDay().isAfter(LocalDate.now().plusYears(-131)))){
        throw new ValidateUserException("BirthDay", "Idade inválida");
    }
    }

}
