package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.controller.Dto.TimeTokenEmailDto;
import br.com.nevesHoteis.controller.Dto.TokenEmailDto;
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

    @PostMapping("")
    ResponseEntity<?> createdOrResendEmail(@RequestBody @Valid TokenEmailDto tokenEmailDto) throws MessagingException, IOException {
        return ResponseEntity.ok( new TimeTokenEmailDto(service.createdOrResend(tokenEmailDto).getTimeSecond()));
    }
    @PostMapping("/verify")
    ResponseEntity<?> verifyToken(@RequestBody @Valid TokenEmailDto tokenEmailDto){
        service.verify(tokenEmailDto);
        return ResponseEntity.ok().build();
    }
}
