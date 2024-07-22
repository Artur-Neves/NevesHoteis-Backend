package br.com.nevesHoteis.controller.dto.user;

import br.com.nevesHoteis.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserDiscretDto (
        @NotNull
        Long id,
        @Email
        String login
){
    public UserDiscretDto(User user){
        this(user.getId(), user.getLogin());
    }
}
