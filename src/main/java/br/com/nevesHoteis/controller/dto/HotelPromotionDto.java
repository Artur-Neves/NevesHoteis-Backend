package br.com.nevesHoteis.controller.dto;

import br.com.nevesHoteis.domain.Hotel;

import java.math.BigDecimal;

public record HotelPromotionDto(
        Long id,
        BigDecimal dailyValue
) {
    public HotelPromotionDto(Hotel hotel) {
        this(hotel.getId(), hotel.getDailyValue());
    }
}
