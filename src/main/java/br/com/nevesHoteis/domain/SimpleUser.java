package br.com.nevesHoteis.domain;

import br.com.nevesHoteis.controller.Dto.PeopleDto;
import br.com.nevesHoteis.controller.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.controller.Dto.SimpleUserDto;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.time.LocalDate;

@Entity()
@Getter

public class SimpleUser extends People {
    public SimpleUser(Long id, String name, LocalDate birthDay, String cpf, String phone, Address address, User user) {
        super(id, name, birthDay, cpf, phone, address, user);
        setRole(Role.USER);
    }

    public SimpleUser() {

    }

    public SimpleUser(PeopleDto dto) {
        super(dto);
        setRole(Role.USER);
    }
    public SimpleUser(PeopleUpdateDto dto){
        super(dto);
        setRole(Role.USER);
    }
    public SimpleUser (SimpleUserDto dto){
        this.name=dto.name();
        this.user= new User(dto.loginDto());
        setRole(Role.USER);
    }

}
