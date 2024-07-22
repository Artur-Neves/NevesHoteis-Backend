package br.com.nevesHoteis.controller.dto.people;

import br.com.nevesHoteis.controller.dto.user.LoginDto;
import br.com.nevesHoteis.controller.dto.address.AddressDto;
import br.com.nevesHoteis.domain.Address;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PeopleDto{
        @NotBlank
        @Size(min = 3, max = 60)
        private String name;
        @NotNull
        @Past
        private LocalDate birthDay;
        @NotBlank
        @Pattern(regexp = "\\d{3}.\\d{3}.\\d{3}-\\d{2}")
        private String cpf;
        @NotBlank
        @Pattern(regexp = "\\d{11}")
        private String phone;
        private MultipartFile profilePicture;
        @NotNull
        @Valid
        private AddressDto address;
        @NotNull
        @Valid
        private LoginDto user;

        public PeopleDto(People people){
        this.name= people.getName();
        this.birthDay = people.getBirthDay();
        this.cpf= people.getCpf();
        this.phone = people.getPhone();
        this.address =  new AddressDto(people.getAddress());
        this.user = new LoginDto(people.getUser());
    }
}
