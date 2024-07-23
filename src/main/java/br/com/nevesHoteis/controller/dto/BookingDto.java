package br.com.nevesHoteis.controller.dto;

import br.com.nevesHoteis.controller.dto.hotel.HotelDto;
import br.com.nevesHoteis.controller.dto.people.SimpleUserDto;
import br.com.nevesHoteis.domain.Booking;
import br.com.nevesHoteis.domain.SimpleUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.nio.file.attribute.UserPrincipal;
import java.time.LocalDate;

public record BookingDto (
        @NotNull
        @Valid
        Long idHotel,
        @NotNull
        @FutureOrPresent
        LocalDate startDate,
        @NotNull
        @Future
        LocalDate endDate
){
    public BookingDto(Booking booking) {
        this( booking.getHotel().getId(),
             booking.getStartDate(), booking.getEndDate());
    }
}
