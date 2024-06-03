package br.com.nevesHoteis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class UserController {
    @GetMapping()
    ResponseEntity<String> helloWorld(){
        return ResponseEntity.ok("Hello World!");
    }
}
