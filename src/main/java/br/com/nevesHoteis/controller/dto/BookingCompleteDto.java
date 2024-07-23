package br.com.nevesHoteis.controller.dto;

import br.com.nevesHoteis.controller.dto.hotel.HotelCompleteDto;
import br.com.nevesHoteis.controller.dto.people.PeopleCompleteDto;
import br.com.nevesHoteis.domain.Booking;
import br.com.nevesHoteis.domain.Hotel;
import br.com.nevesHoteis.domain.SimpleUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookingCompleteDto (
        Long id,
        PeopleCompleteDto simpleUser,
        HotelCompleteDto hotel,
        LocalDate startDate,
        LocalDate endDate
){
    public BookingCompleteDto(Booking booking) {
        this(booking.getId() , new PeopleCompleteDto(booking.getSimpleUser()), new HotelCompleteDto(booking.getHotel()),
                booking.getStartDate(), booking.getEndDate());
    }
}