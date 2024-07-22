package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.controller.dto.people.PeopleCompleteDto;
import br.com.nevesHoteis.controller.dto.people.PeopleDto;
import br.com.nevesHoteis.controller.dto.people.PeopleUpdateDto;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.service.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static br.com.nevesHoteis.infra.factory.PeopleFactory.createdNewPeople;


public class PeopleController <T extends People, S extends PeopleService<T> > {
    @Autowired
    protected S service;
    private T t;

    public PeopleController(T t) {
        this.t = t;
    }

    @GetMapping()
    ResponseEntity<Page<PeopleCompleteDto>> findAll(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(service.findAll(pageable).map(PeopleCompleteDto::new));
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<PeopleCompleteDto> save(@ModelAttribute @Valid PeopleDto dto, UriComponentsBuilder uriComponentsBuilder){
        People people = service.save( createdNewPeople(t.getClass() , dto));
        URI uri = uriComponentsBuilder.path("{id}").buildAndExpand(people).toUri();
        return ResponseEntity.created(uri).body( new PeopleCompleteDto(people));
    }
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/{id}")
    ResponseEntity<PeopleCompleteDto> update(@ModelAttribute @Valid PeopleUpdateDto dto, @PathVariable String id){
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
