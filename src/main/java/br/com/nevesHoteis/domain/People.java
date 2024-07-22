package br.com.nevesHoteis.domain;

import br.com.nevesHoteis.controller.dto.people.PeopleAddressDataDto;
import br.com.nevesHoteis.controller.dto.people.PeopleDto;
import br.com.nevesHoteis.controller.dto.people.PeoplePersonalDataDto;
import br.com.nevesHoteis.controller.dto.people.PeopleUpdateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;

import static br.com.nevesHoteis.infra.utils.Conversions.convertMultiPartFileInByte;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;
    protected LocalDate birthDay;
    protected String cpf;
    protected String phone;
    @Embedded
    protected Address address;
    @OneToOne(cascade = CascadeType.ALL)
    protected User user;
    protected byte[] profilePicture;


    public People(Long id, String name, LocalDate birthDay, String cpf, String phone, Address address, User user) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.cpf = cpf;
        this.phone = phone;
        this.address = address;
        this.user = user;
    }

    public People(PeopleDto dto) {
        this.name = dto.getName();
        this.birthDay = dto.getBirthDay();
        this.cpf = dto.getCpf();
        this.phone = dto.getPhone();
        this.address = new Address(dto.getAddress());
        this.user = new User(dto.getUser());
        this.profilePicture= convertMultiPartFileInByte(dto.getProfilePicture());
    }

    public People(PeopleUpdateDto dto) {
        this.name = dto.getName();
        this.birthDay = dto.getBirthDay();
        this.cpf = dto.getCpf();
        this.phone = dto.getPhone();
        this.address = new Address(dto.getAddress());
        this.profilePicture= convertMultiPartFileInByte(dto.getProfilePicture());
    }

    public People(PeoplePersonalDataDto dto) {
        this.name = dto.getName();
        this.birthDay = dto.getBirthDay();
        this.cpf = dto.getCpf();
        this.phone = dto.getPhone();
        this.profilePicture =  convertMultiPartFileInByte(dto.getProfilePicture());

    }

    public People(PeopleAddressDataDto dto) {
        this.name = dto.name();
        this.birthDay = dto.birthDay();
        this.cpf = dto.cpf();
        this.phone = dto.phone();
        this.address = new Address(dto.address());
    }

    public void setRole(Role role) {
        this.user.setRole(role);
    }

    public People merge(People people) {
        this.name = people.getName();
        this.birthDay = people.getBirthDay();
        this.cpf = people.getCpf();
        this.phone = people.getPhone();
        this.profilePicture = people.getProfilePicture();
        if (people.getAddress() != null) {
            if (this.address == null)
                this.address = new Address();
            this.address.merge(people.getAddress());
        }
        return this;
    }


}
