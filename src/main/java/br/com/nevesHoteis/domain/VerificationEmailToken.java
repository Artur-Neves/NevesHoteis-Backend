package br.com.nevesHoteis.domain;

import br.com.nevesHoteis.infra.exeption.EmailTokenException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDateTime;

import static br.com.nevesHoteis.infra.utils.CodeGenerator.generateCode;

@Entity
@Getter
@NoArgsConstructor
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
    private int timeSecond= 30;
    private LocalDateTime resendTime = LocalDateTime.now().plusSeconds(timeSecond) ;

    //private final int minutesExpiry = 10;

    public VerificationEmailToken(User user, String token) {
        this.user = user;
        restartExpiryDate();
        this.token = token;
    }
    public VerificationEmailToken resendEmail(String token){
        if (LocalDateTime.now().isAfter(resendTime)){
        this.token=token;
        this.timeSecond += timeSecond/2;
        this.resendTime= LocalDateTime.now().plusSeconds(timeSecond);
        restartExpiryDate();
        return this;
        }
        else {
            throw new EmailTokenException("Tempo para o reenvio do email", "Espere o tempo necessário para pedir outro reenvio");
        }
    }
    public void compareToken(String token) {
        if (LocalDateTime.now().isBefore(expiryDate)) {
            if (this.token.equals(token)) {
                correctToken();
            } else {
                throw new EmailTokenException("Token incorreto", "Token incorreto");
            }
        } else {
            try {
                resendEmail(    generateCode());
            } catch (Exception e) {
                throw new EmailTokenException("Código expirado", "Espere até poder realizar o reenvio novamente");
            }
        }
    }
    private void restartExpiryDate(){
        this.expiryDate = LocalDateTime.now().plusMinutes(10);
    }

    private void correctToken() {
        this.timeSecond = 30;
        resendTime= LocalDateTime.now().plusSeconds(timeSecond);
        this.user.setEnabled(true);
    }
}
