package br.com.nevesHoteis.controller.Dto;

import br.com.nevesHoteis.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
