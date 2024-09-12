package br.com.nevesHoteis.repository.projections;

import br.com.nevesHoteis.domain.Promotion;

import java.math.BigDecimal;

public record PromotionCardProjection(
        BigDecimal discount,
        int percentageDiscount
) {
    public PromotionCardProjection(Promotion promotion) {
        this(promotion.getDiscount(), promotion.getPercentageDiscount());
    }

    public PromotionCardProjection(BigDecimal discount, int percentageDiscount) {
        this.discount = discount;
        this.percentageDiscount = percentageDiscount;
    }
}
