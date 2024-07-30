package br.com.nevesHoteis.domain;

import br.com.nevesHoteis.controller.dto.people.PeopleDto;
import br.com.nevesHoteis.controller.dto.people.PeopleUpdateDto;
import br.com.nevesHoteis.controller.dto.people.SimpleUserDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Getter()
public class SimpleUser extends People {
    @OneToMany(mappedBy = "simpleUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    public SimpleUser() {
    }

    public SimpleUser(Long id) {
        super(id);
    }

    public SimpleUser(PeopleUpdateDto dto){
        super(dto);
    }

    public SimpleUser(PeopleDto dto) {
        super(dto);
        setRole(Role.USER);
    }

    public SimpleUser (SimpleUserDto dto){
        this.name=dto.name();
        this.user= new User(dto.loginDto());
        setRole(Role.USER);
    }

    public SimpleUser(Long id, String name, LocalDate birthDay, String cpf, String phone, Address address, User user) {
        super(id, name, birthDay, cpf, phone, address, user);
        setRole(Role.USER);
    }

}
