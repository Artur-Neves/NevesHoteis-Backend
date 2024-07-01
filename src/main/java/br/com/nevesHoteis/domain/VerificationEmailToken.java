package br.com.nevesHoteis.domain;

import br.com.nevesHoteis.infra.exeption.EmailTokenException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static br.com.nevesHoteis.infra.utils.CodeGenerator.generateCode;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationEmailToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.MERGE, optional = false)
    @MapsId
    private User user;
    @Setter
    private String token;
    private LocalDateTime expiryDate;
    private int resendIntervalSeconds = 30;
    private LocalDateTime resendTime = LocalDateTime.now().plusSeconds(resendIntervalSeconds);

    public VerificationEmailToken(User user, String token) {
        this.user = user;
        restartExpiryDate();
        this.token = token;
    }
    public VerificationEmailToken resendToken(String token){
        this.setToken(token);
        this.incrementResendInterval();
        this.restartExpiryDate();
        return this;
    }

    public void restartExpiryDate() {
        this.expiryDate = LocalDateTime.now().plusMinutes(10);
    }

    public  void incrementResendInterval() {
        this.resendIntervalSeconds += resendIntervalSeconds / 2;
        this.resendTime = LocalDateTime.now().plusSeconds(resendIntervalSeconds);
    }

    public void activeUser() {
        this.resendIntervalSeconds = 30;
        resendTime = LocalDateTime.now().plusSeconds(resendIntervalSeconds);
        this.user.setEnabled(true);
    }
}
