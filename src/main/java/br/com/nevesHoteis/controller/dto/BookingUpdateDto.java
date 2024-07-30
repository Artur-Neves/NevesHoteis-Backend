package br.com.nevesHoteis.controller.dto;

import br.com.nevesHoteis.domain.Booking;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookingUpdateDto (
        @NotNull
        @FutureOrPresent
        LocalDate startDate,
        @NotNull
        @Future
        LocalDate endDate
){
        public BookingUpdateDto(Booking booking){
                this(booking.getStartDate(), booking.getEndDate());
        }

}
