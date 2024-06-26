package br.com.nevesHoteis.domain;

import br.com.nevesHoteis.controller.Dto.PeopleDto;
import br.com.nevesHoteis.controller.Dto.PeopleUpdateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;
    protected LocalDate birthDay;
    protected String cpf;
    protected String phone;
    @Embedded
    protected Address address;
    @OneToOne( cascade = CascadeType.ALL)
    protected User user;

    public People(PeopleDto dto){
        this.name = dto.name();
        this.birthDay = dto.birthDay();
        this.cpf = dto.cpf();
        this.phone = dto.phone();
        this.address = new Address(dto.address());
        this.user = new User(dto.user());
    }
    public People(PeopleUpdateDto dto){
        this.name = dto.name();
        this.birthDay = dto.birthDay();
        this.cpf = dto.cpf();
        this.phone = dto.phone();
        this.address = new Address(dto.address());
        this.user = new User(dto.user());
    }
    public void setRole(Role role){
        this.user.setRole(role);
    }
    public People merge(People people){
        this.name = people.getName();
        this.birthDay = people.getBirthDay();
        this.cpf = people.getCpf();
        this.phone = people.getPhone();
        this.address.merge(people.getAddress());
        this.user.merge(people.getUser());
        return this;
    }






}
