package br.com.nevesHoteis.controller.dto.people;

import br.com.nevesHoteis.controller.dto.user.LoginDto;
import br.com.nevesHoteis.domain.Address;
import br.com.nevesHoteis.domain.SimpleUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SimpleUserDto(
        Long id,
        @NotBlank
        @Size(min= 3, max=30)
        String name,
        LoginDto loginDto,
        Address address
) {
        public SimpleUserDto(SimpleUser user) {
                this(user.getId(), user.getName(), new LoginDto(user.getUser()), new Address());
        }
}
