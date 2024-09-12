package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.controller.dto.hotel.HotelCompleteDto;

import br.com.nevesHoteis.controller.dto.hotel.HotelDto;
import br.com.nevesHoteis.controller.dto.user.LoginDto;
import br.com.nevesHoteis.domain.Hotel;
import br.com.nevesHoteis.repository.projections.HotelDatesCardProjection;
import br.com.nevesHoteis.service.HotelService;
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

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService service;
    @GetMapping
    ResponseEntity<Page<HotelDatesCardProjection>> findAllHotel (@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(service.findAll(pageable));
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<HotelCompleteDto> saveHotel(@Valid @ModelAttribute HotelDto HotelDto, UriComponentsBuilder uriComponentsBuilder){
        Hotel hotel = service.save(new Hotel(HotelDto));
        URI uri = uriComponentsBuilder.path("/{id}").buildAndExpand(hotel.getId()).toUri();
        return ResponseEntity.created(uri).body(new HotelCompleteDto(hotel));
    }
    @GetMapping("/{id}")
    ResponseEntity<HotelCompleteDto> findByIdHotel(@PathVariable Long id){
        return ResponseEntity.ok(new HotelCompleteDto(service.findById(id)));
    }
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/{id}")
    ResponseEntity<HotelCompleteDto> updateHotel(@PathVariable Long id, @ModelAttribute HotelDto hotelDto){
        return ResponseEntity.ok(new HotelCompleteDto(service.update(id, new Hotel(hotelDto))));
    }
    @DeleteMapping("/{id}")
    ResponseEntity<HotelCompleteDto> deleteHotel(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
