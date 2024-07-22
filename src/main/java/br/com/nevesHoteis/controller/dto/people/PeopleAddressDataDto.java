package br.com.nevesHoteis.controller.dto.people;

import br.com.nevesHoteis.controller.dto.address.AddressDto;
import br.com.nevesHoteis.domain.People;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PeopleAddressDataDto(
        String name,
        LocalDate birthDay,
        String cpf,
        String phone,
        @NotNull
        @Valid
        AddressDto address) {
    public PeopleAddressDataDto(People people){
        this( people.getName(), people.getBirthDay(), people.getCpf(),
                people.getPhone(), new AddressDto(people.getAddress()));
    }
}
