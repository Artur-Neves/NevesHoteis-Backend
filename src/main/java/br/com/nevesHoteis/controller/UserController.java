package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.controller.dto.people.PeopleAddressDataDto;
import br.com.nevesHoteis.controller.dto.people.PeopleCompleteDto;
import br.com.nevesHoteis.controller.dto.people.PeoplePersonalDataDto;
import br.com.nevesHoteis.controller.dto.token.TokenDto;
import br.com.nevesHoteis.controller.dto.user.LoginDto;
import br.com.nevesHoteis.controller.dto.user.RedefinePasswordDto;
import br.com.nevesHoteis.controller.dto.user.UserDiscretDto;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.service.TokenService;
import br.com.nevesHoteis.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken userToken= new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword());
        Authentication authentication =manager.authenticate(userToken);
        return ResponseEntity.ok(tokenService.tokensAfterLoginToken((User) authentication.getPrincipal()));
    }
    @PostMapping("/refresh")
    ResponseEntity<TokenDto> refresh(@RequestBody TokenDto token){
        return ResponseEntity.ok(new TokenDto(tokenService.refreshToken(token.token())));
    }
    @GetMapping("/{email}")
    ResponseEntity<UserDiscretDto> findUserByLogin(@PathVariable String email){
        return ResponseEntity.ok( new UserDiscretDto(service.findByLogin(email)));
    }

    @GetMapping("/myAccount")
    ResponseEntity<PeopleCompleteDto> findPeopleByLogin(){
        return ResponseEntity.ok( new PeopleCompleteDto(service.findPeopleByLogin()));
    }
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/updatePersonalDataAccount")
    ResponseEntity<PeopleCompleteDto> updatePersonalDataAccount(@ModelAttribute @Valid PeoplePersonalDataDto dto){
        return ResponseEntity.ok( new PeopleCompleteDto(service.updateMyAccount(new People(dto))));
    }
    @PutMapping("/updateAddressAccount")
    ResponseEntity<PeopleCompleteDto> updateAddressAccount(@RequestBody @Valid PeopleAddressDataDto dto){
        return ResponseEntity.ok( new PeopleCompleteDto(service.updateMyAccount(new People(dto))));
    }
    @PutMapping("/redefine-password")
    ResponseEntity<String> redefinePassword(@RequestBody @Valid RedefinePasswordDto dto){
        service.redefinePassword(dto);
        return ResponseEntity.noContent().build();
    }

}
