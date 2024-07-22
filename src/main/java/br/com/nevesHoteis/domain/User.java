package br.com.nevesHoteis.domain;

import br.com.nevesHoteis.controller.dto.user.LoginDto;
import br.com.nevesHoteis.controller.dto.user.UserDiscretDto;
import br.com.nevesHoteis.controller.dto.user.UserUpdateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.*;
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_entity")
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String login;

    private boolean enabled = false;

    private String password;
    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "user")
    private VerificationEmailToken verificationEmailToken;

    public User (LoginDto dto){
        this.login = dto.getLogin();
        this.password = dto.getPassword();
    }
    public User (UserDiscretDto dto){
        this.id = dto.id();
        this.login = dto.login();
    }

    public User(UserUpdateDto dto) {
        this.password = dto.password();
    }

    public User(String login, String password, Role role) {
        this.login=login;
        this.password=password;
        this.role=role;
    }


    public void merge(User user) {
        this.password=user.getPassword();
        passwordEncoder();
    }

    public void redefinePassword(String newPassword) {
        this.password=  newPassword;
        passwordEncoder();
    }

    public void passwordEncoder() {
      this.setPassword(BCrypt.hashpw(getPassword(), BCrypt.gensalt()));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", enabled=" + enabled +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", verificationEmailToken=" + verificationEmailToken +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
