package br.com.nevesHoteis.domain.Dto;

import br.com.nevesHoteis.domain.Address;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

public record PeopleDto(
        @NotBlank
        String name,
        @NotNull
        @Past
        LocalDate birthDay,
        @NotBlank
        String cpf,
        @NotBlank
        String phone,
        @NotNull
        @Valid
        AddressDto address,
        @NotNull
        @Valid
        LoginDto user
) {
    public PeopleDto(People people){
        this(people.getName(), people.getBirthDay(), people.getCpf(),
             people.getPhone(), new AddressDto(people.getAddress()), new LoginDto(people.getUser()));
    }
}
