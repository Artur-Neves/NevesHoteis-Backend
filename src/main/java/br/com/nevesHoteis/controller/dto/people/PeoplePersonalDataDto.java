package br.com.nevesHoteis.controller.dto.people;

import br.com.nevesHoteis.domain.People;
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
public class PeoplePersonalDataDto {
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

    public PeoplePersonalDataDto(People people) {
        this(people.getName(), people.getBirthDay(), people.getCpf(),
                people.getPhone(), null);
    }
}
