package br.com.nevesHoteis.controller.dto;

import br.com.nevesHoteis.domain.Promotion;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PromotionUpdateDto(
        @NotNull
        @Min(0)
        BigDecimal discount,
        @FutureOrPresent
        LocalDateTime endDate
) {
        public PromotionUpdateDto(Promotion promotion){
                this(promotion.getDiscount(), promotion.getEndDate());
        }
}
