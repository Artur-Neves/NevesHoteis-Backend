package br.com.nevesHoteis.controller.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RedefinePasswordDto(
        @Email
        @NotBlank
        String email,
        @NotBlank
        @Size(min = 8, max = 30)
        String newPassword,
        @NotBlank
        @Size(min = 8, max = 30)
        String repeatPassword,
        @NotBlank
        @Size(min = 6, max = 6)
        String token
) {
}
