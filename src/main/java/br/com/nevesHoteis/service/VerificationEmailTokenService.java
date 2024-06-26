package br.com.nevesHoteis.service;

import br.com.nevesHoteis.controller.Dto.TokenEmailDto;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.domain.VerificationEmailToken;
import br.com.nevesHoteis.repository.UserRepository;
import br.com.nevesHoteis.repository.VerificationEmailTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static br.com.nevesHoteis.infra.utils.CodeGenerator.generateCode;

@Service
public class VerificationEmailTokenService {
    @Autowired
    private VerificationEmailTokenRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Transactional
    public VerificationEmailToken createdOrResend(TokenEmailDto tokenEmailDto) throws MessagingException, IOException {
        User user = transformDtoInUser(tokenEmailDto);
        var optionalToken = repository.findById(user.getId());
        String token = generateCode();
        if(optionalToken.isPresent()){
            var verification = optionalToken.get().resendEmail(token);
            emailService.sendConfirmEmail(user.getLogin(), token);
            return verification;
        }
        emailService.sendConfirmEmail(user.getLogin(), token);
        return saveToken(user, token);
    }
    private User transformDtoInUser (TokenEmailDto dto){
        return userRepository.findByLogin(dto.email()).orElseThrow(EntityNotFoundException::new);
    }
    public VerificationEmailToken saveToken(User user, String token) {
        var verificationEmailToken = new VerificationEmailToken(user, token);
        return repository.save(verificationEmailToken);
    }
    @Transactional
    public void verify(TokenEmailDto dto){
        User user = transformDtoInUser(dto);
        VerificationEmailToken verifyToken = repository.findById(user.getId()).orElseThrow(EntityNotFoundException::new);
        verifyToken.compareToken(dto.token());
    }
}
