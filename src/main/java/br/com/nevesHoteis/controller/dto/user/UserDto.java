package br.com.nevesHoteis.controller.dto.user;

import br.com.nevesHoteis.domain.Role;
import br.com.nevesHoteis.domain.User;
import jakarta.validation.constraints.Email;
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
