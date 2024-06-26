package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.controller.Dto.*;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.service.TokenService;
import br.com.nevesHoteis.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService service;
    @PostMapping("/login")
    ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken userToken= new UsernamePasswordAuthenticationToken(loginDto.login(), loginDto.password());
        Authentication authentication =manager.authenticate(userToken);
        return ResponseEntity.ok(new TokenDto(tokenService.createdToken((User) authentication.getPrincipal())));
    }
    @GetMapping("/{email}")
    ResponseEntity<?> findUserByLogin(@PathVariable String email){
        return ResponseEntity.ok( new UserDiscretDto(service.findByLogin(email)));
    }
    @PutMapping("/redefine-password")
    ResponseEntity<String> redefinePassword(@RequestBody @Valid  RedefinePasswordDto dto){
        service.redefinePassword(dto);
        return ResponseEntity.ok().build();
    }

}
