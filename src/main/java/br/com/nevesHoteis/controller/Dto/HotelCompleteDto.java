package br.com.nevesHoteis.controller.Dto;

import br.com.nevesHoteis.domain.Hotel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HotelCompleteDto (
         Long id,
         String name,
         LocalDateTime availabilityDate,
         BigDecimal dailyValue,
         AddressCompleteDto address
){
    public HotelCompleteDto(Hotel hotel) {
        this(hotel.getId(), hotel.getName(), hotel.getAvailabilityDate()
        , hotel.getDailyValue(), new AddressCompleteDto( hotel.getAddress()));
    }
}
