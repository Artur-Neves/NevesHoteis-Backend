package br.com.nevesHoteis.controller.dto;

import br.com.nevesHoteis.controller.dto.hotel.HotelDto;
import br.com.nevesHoteis.domain.Promotion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PromotionCompleteDto(
        Long id,
        BigDecimal discount,
        double percentageDiscount,
        LocalDateTime startDate,
        LocalDateTime endDate,
        boolean deleted,
        HotelDto hotel
        ){
    public PromotionCompleteDto(Promotion promotion){
        this(promotion.getId(), promotion.getDiscount(), promotion.getPercentageDiscount(), promotion.getStartDate(), promotion.getEndDate(),  promotion.isDeleted(), new HotelDto(promotion.getHotel()));

    }
};
