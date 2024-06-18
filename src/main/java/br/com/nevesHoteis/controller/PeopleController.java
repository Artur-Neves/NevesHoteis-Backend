package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.domain.Dto.PeopleCompleteDto;
import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.service.PeopleService;
import br.com.nevesHoteis.service.SimpleUserService;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.function.Function;

import static br.com.nevesHoteis.infra.factory.PeopleFactory.createdNewPeople;


public class PeopleController <T extends People, S extends PeopleService<T> > {
    @Autowired
    private S service;
    private T t;

    public PeopleController(T t) {
        this.t = t;
    }

    @GetMapping()
    ResponseEntity<Page<PeopleCompleteDto>> findAll(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(service.findAll(pageable).map(PeopleCompleteDto::new));
    }
    @PostMapping
    ResponseEntity<PeopleCompleteDto> save(@RequestBody @Valid PeopleDto dto, UriComponentsBuilder uriComponentsBuilder){
        People people = service.save( createdNewPeople(t.getClass() , dto));
        URI uri = uriComponentsBuilder.path("{id}").buildAndExpand(people).toUri();
        return ResponseEntity.created(uri).body( new PeopleCompleteDto(people));
    }
    @PutMapping("/{id}")
    ResponseEntity<PeopleCompleteDto> update(@RequestBody @Valid PeopleUpdateDto dto, @PathVariable Long id){
        return ResponseEntity.ok(new PeopleCompleteDto(service.update(id, createdNewPeople( t.getClass() , dto))));
    }
    @GetMapping("/{id}")
    ResponseEntity<PeopleCompleteDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(new PeopleCompleteDto(service.findById(id)));
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
