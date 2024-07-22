package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.controller.dto.token.TimeTokenEmailDto;
import br.com.nevesHoteis.controller.dto.token.TokenEmailDto;
import br.com.nevesHoteis.service.VerificationEmailTokenService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/email-token")
public class VerificationEmailTokenController {
    @Autowired
    private VerificationEmailTokenService service;

    @PostMapping()
    ResponseEntity<?> createdOrResendEmail(@RequestBody @Valid TokenEmailDto tokenEmailDto) throws MessagingException, IOException {
        TimeTokenEmailDto responseDto = new TimeTokenEmailDto(service.createdOrResend(tokenEmailDto).getResendIntervalSeconds());
        return ResponseEntity.ok( responseDto);
    }
    @PostMapping("/verify")
    ResponseEntity<?> verifyToken(@RequestBody @Valid TokenEmailDto tokenEmailDto){
        service.verify(tokenEmailDto);
        return ResponseEntity.noContent().build();
    }
}
