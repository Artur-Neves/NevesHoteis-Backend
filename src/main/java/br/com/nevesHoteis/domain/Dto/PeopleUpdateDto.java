package br.com.nevesHoteis.domain.Dto;

import br.com.nevesHoteis.domain.People;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.Date;

public record PeopleUpdateDto(
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
        UserUpdateDto user
) {
    public PeopleUpdateDto(People people){
        this( people.getName(), people.getBirthDay(), people.getCpf(),
                people.getPhone(), new AddressDto(people.getAddress()), new UserUpdateDto(people.getUser()));
    }
}
