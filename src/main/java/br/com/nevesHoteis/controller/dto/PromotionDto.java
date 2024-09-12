package br.com.nevesHoteis.controller.dto;

import br.com.nevesHoteis.controller.dto.hotel.HotelDto;
import br.com.nevesHoteis.domain.Promotion;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PromotionDto (
        @NotNull
        @Min(0)
        BigDecimal discount,
        @FutureOrPresent
        LocalDateTime startDate,
        @FutureOrPresent
        LocalDateTime endDate,
        @NotNull
        Long idHotel
        ){
    public PromotionDto(Promotion promotion){
        this(promotion.getDiscount(), promotion.getStartDate(), promotion.getEndDate(), promotion.getHotel().getId());

    }
};
