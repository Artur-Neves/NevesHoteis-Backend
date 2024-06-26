package br.com.nevesHoteis.controller.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record TokenEmailDto (
        @Email
        @NotBlank
        String email,
        String token){

}
