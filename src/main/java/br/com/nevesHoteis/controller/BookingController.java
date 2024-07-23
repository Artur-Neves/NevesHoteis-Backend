package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.controller.dto.BookingCompleteDto;
import br.com.nevesHoteis.controller.dto.BookingDto;
import br.com.nevesHoteis.controller.dto.BookingUpdateDto;
import br.com.nevesHoteis.domain.Booking;
import br.com.nevesHoteis.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService service;

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Page<BookingCompleteDto>> findAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable).map(BookingCompleteDto::new));
    }

    @GetMapping("/{id}")
    ResponseEntity<BookingCompleteDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(new BookingCompleteDto(service.findById(id)));
    }

    @PostMapping()
    ResponseEntity<BookingCompleteDto> save(@RequestBody @Valid BookingDto bookingDto,
                                            UriComponentsBuilder uriComponentsBuilder) {
        Booking booking = service.save(bookingDto);
        URI uri = uriComponentsBuilder.path("/booking/{id}").buildAndExpand(booking.getId()).toUri();
        return ResponseEntity.created(uri).body(new BookingCompleteDto(booking));
    }

    @PutMapping("/{id}")
    ResponseEntity<BookingCompleteDto> update(
            @RequestBody @Valid BookingUpdateDto bookingUpdateDto,
            @PathVariable Long id) {
        return ResponseEntity.ok(new BookingCompleteDto(service.updateDates(bookingUpdateDto, id)));
    }

    @DeleteMapping("/{id}")
    ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
