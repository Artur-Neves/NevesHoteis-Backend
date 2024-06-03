package br.com.nevesHoteis.domain.Dto;

import br.com.nevesHoteis.domain.Hotel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HotelUpdateDto (
        @NotNull
        Long id,
        @NotBlank
        String name,
        @NotNull
        LocalDateTime availabilityDate,
        @NotNull
        BigDecimal dailyValue,
        @NotNull
        AddressCompleteDto address
){
    HotelUpdateDto(Hotel hotel) {
        this(hotel.getId(), hotel.getName(), hotel.getAvailabilityDate()
                , hotel.getDailyValue(), new AddressCompleteDto( hotel.getAddress()));
    }
}