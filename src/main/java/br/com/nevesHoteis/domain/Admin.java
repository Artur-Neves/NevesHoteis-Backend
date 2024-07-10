package br.com.nevesHoteis.domain;

import br.com.nevesHoteis.controller.Dto.PeopleDto;
import br.com.nevesHoteis.controller.Dto.PeopleUpdateDto;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Admin extends People {
    public Admin(Long id, String name, LocalDate birthDay, String cpf, String phone, Address address, User user) {
        super(id, name, birthDay, cpf, phone, address, user);
        setRole(Role.ADMIN);
    }

    public Admin() {

    }
    public Admin(PeopleDto dto) {
        super(dto);
        setRole(Role.ADMIN);
    }
    public Admin(PeopleUpdateDto dto){
        super(dto);
    }
}
