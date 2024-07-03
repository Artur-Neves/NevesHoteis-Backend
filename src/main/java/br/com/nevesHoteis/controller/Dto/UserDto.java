package br.com.nevesHoteis.controller.Dto;

import br.com.nevesHoteis.domain.Role;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.domain.VerificationEmailToken;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDto(
        @Email
        String login,
        @NotNull
        Role role
) {
    public UserDto(User user){
        this(user.getLogin(),  user.getRole());
    }
}
