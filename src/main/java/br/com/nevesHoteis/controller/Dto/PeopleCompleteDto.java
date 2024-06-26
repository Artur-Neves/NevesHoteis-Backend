package br.com.nevesHoteis.controller.Dto;

import br.com.nevesHoteis.domain.People;

import java.time.LocalDate;

public record PeopleCompleteDto (
        Long id,
        String name,
        LocalDate birthDay,
        String cpf,
        String phone,
        AddressCompleteDto address,
        UserDto user
){
    public PeopleCompleteDto(People people){
        this(people.getId(), people.getName(), people.getBirthDay(), people.getCpf(),
                people.getPhone(), new AddressCompleteDto(people.getAddress()), new UserDto(people.getUser()));
    }
}
