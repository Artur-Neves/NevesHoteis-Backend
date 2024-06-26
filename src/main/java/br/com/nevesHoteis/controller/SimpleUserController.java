package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.controller.Dto.SimpleUserDto;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.service.SimpleUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequestMapping("/simple-user")
@RestController
public class SimpleUserController extends PeopleController<SimpleUser, SimpleUserService> {
    public SimpleUserController() {
        super(new SimpleUser());
    }
    @PostMapping("/create-simple")
    ResponseEntity<SimpleUserDto> createSimple(@RequestBody @Valid SimpleUserDto userSimpleDto, UriComponentsBuilder uriComponentsBuilder){
        SimpleUser simpleUser = service.simpleSave( new SimpleUser(userSimpleDto));
        URI uri = uriComponentsBuilder.path("/simple-user/{id}").buildAndExpand(simpleUser.getId()).toUri();
        return  ResponseEntity.created(uri).body(new SimpleUserDto(simpleUser));
    }
}
