package br.com.nevesHoteis.service;

import br.com.nevesHoteis.controller.Dto.TokenEmailDto;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.domain.VerificationEmailToken;
import br.com.nevesHoteis.infra.exeption.EmailTokenException;
import br.com.nevesHoteis.repository.UserRepository;
import br.com.nevesHoteis.repository.VerificationEmailTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

import static br.com.nevesHoteis.infra.utils.CodeGenerator.generateCode;

@Service
public class VerificationEmailTokenService {
    @Autowired
    private VerificationEmailTokenRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    private final LocalDateTime TO_DAY = LocalDateTime.now();
    @Transactional
    public VerificationEmailToken createdOrResend(TokenEmailDto dto) throws MessagingException, IOException {
        var optionalToken= repository.findByUserLogin(dto.email());
        String token = generateCode();
        VerificationEmailToken verification = (optionalToken.isPresent() ? resendEmail(optionalToken.get(),token): saveToken(dto.email(), token));
        emailService.sendConfirmEmail(dto.email(), token);
        return verification;
    }
    private VerificationEmailToken findByLogin (String login){
        return repository.findByUserLogin(login).orElseThrow(EntityNotFoundException::new);
    }
    private VerificationEmailToken saveToken(String email, String token) {
        User user = userRepository.findByLogin(email).orElseThrow(EntityNotFoundException::new);
        var verificationEmailToken = new VerificationEmailToken(user, token);
        return repository.save(verificationEmailToken);
    }
    @Transactional
    public void verify(TokenEmailDto dto){
        VerificationEmailToken verifyToken = findByLogin(dto.email());
        compareToken(verifyToken, dto.token());
    }

    private VerificationEmailToken resendEmail(VerificationEmailToken verificationToken, String token) {
        if (TO_DAY.isAfter(verificationToken.getResendTime())) {
            return verificationToken.resendToken(token);
        } else {
            throw new EmailTokenException("Tempo para o reenvio do email", "Espere o tempo necessário para pedir outro reenvio");
        }
    }

    private void compareToken(VerificationEmailToken verificationToken, String token) {
        if (TO_DAY.isBefore(verificationToken.getExpiryDate())) {
            if (verificationToken.getToken().equals(token)) {
                verificationToken.activeUser();
            } else {
                throw new EmailTokenException("Token incorreto", "Token incorreto");
            }
        } else {
                throw new EmailTokenException("Código expirado", "realize  o reenvio do email");
            }
        }
}
