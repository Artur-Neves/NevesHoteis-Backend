package br.com.nevesHoteis.controller.dto.user;

import br.com.nevesHoteis.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto{
        private Long id;
        @Email
        private String login;
        @NotBlank
        @Size(min = 8, max = 30)
        private String password;


        public LoginDto(User user){
                this.id = user.getId();
                this.login = user.getLogin();
                this.password = user.getPassword();
        }
}
