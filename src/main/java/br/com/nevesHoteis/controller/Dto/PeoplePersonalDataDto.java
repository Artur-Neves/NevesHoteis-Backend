package br.com.nevesHoteis.controller.Dto;

import br.com.nevesHoteis.domain.People;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PeoplePersonalDataDto(
        @NotBlank
        @Size(min = 3, max = 60)
        String name,
        @NotNull
        @Past
        LocalDate birthDay,
        @NotBlank
        @Pattern(regexp = "\\d{3}.\\d{3}.\\d{3}-\\d{2}")
        String cpf,
        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String phone) {
    public PeoplePersonalDataDto(People people){
        this( people.getName(), people.getBirthDay(), people.getCpf(),
                people.getPhone());
    }
}
