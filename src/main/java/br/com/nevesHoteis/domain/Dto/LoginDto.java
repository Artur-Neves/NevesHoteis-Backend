package br.com.nevesHoteis.domain.Dto;

import br.com.nevesHoteis.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @Email
        String login,
        @NotBlank
        String password
) {
        public LoginDto(User user){
                this(user.getLogin(), user.getPassword());
        }

}
