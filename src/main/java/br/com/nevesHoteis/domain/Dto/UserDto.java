package br.com.nevesHoteis.domain.Dto;

import jakarta.validation.constraints.NotBlank;

public record UserDto(
        @NotBlank
        String login,
        @NotBlank
        String password
) {

}
