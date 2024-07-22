package br.com.nevesHoteis.controller.dto.people;

import br.com.nevesHoteis.controller.dto.user.UserDto;
import br.com.nevesHoteis.controller.dto.address.AddressCompleteDto;
import br.com.nevesHoteis.domain.People;

import java.time.LocalDate;

public record PeopleCompleteDto (
        Long id,
        String name,
        LocalDate birthDay,
        String cpf,
        String phone,
        byte[] profilePicture,
        AddressCompleteDto address,
        UserDto userDto
){
    public PeopleCompleteDto(People people){
        this(people.getId(), people.getName(), people.getBirthDay(), people.getCpf(),
                people.getPhone(), people.getProfilePicture(),
                (people.getAddress()!=null) ? new AddressCompleteDto(people.getAddress()): null,
                new UserDto(people.getUser()));
    }
}
