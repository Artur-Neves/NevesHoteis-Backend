package br.com.nevesHoteis.controller.Dto;

import br.com.nevesHoteis.domain.Hotel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public record HotelDto(
        @NotBlank
        @Size( min = 2, max = 30)
        String name,
        @NotNull
        @FutureOrPresent
        LocalDate availabilityDate,
        @NotNull
        @Positive
        @Max(999999)
        BigDecimal dailyValue,
        @NotNull
        @Valid
        AddressDto address
){
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public HotelDto(Hotel hotel) {

        this(hotel.getName(), hotel.getAvailabilityDate()
                , hotel.getDailyValue(), new AddressDto( hotel.getAddress()));
    }
}