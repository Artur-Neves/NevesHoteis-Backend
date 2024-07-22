package br.com.nevesHoteis.controller.dto.user;

import br.com.nevesHoteis.domain.User;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateDto(
        @NotBlank
        String password
) {
    public UserUpdateDto(User user){
        this( user.getPassword());
    }
}
