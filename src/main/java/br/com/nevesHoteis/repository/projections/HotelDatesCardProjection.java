package br.com.nevesHoteis.repository.projections;

import br.com.nevesHoteis.domain.Hotel;

import java.math.BigDecimal;
import java.util.List;

public record HotelDatesCardProjection(
        String name,
        boolean inPromotion,
        BigDecimal dailyValue,
        BigDecimal realDailyValue,
        List<byte[]> photos,
        AddressHotelCardProjection address,
        PromotionCardProjection promotion
){
    public HotelDatesCardProjection(Hotel hotel) {
        this(hotel.getName(), hotel.isInPromotion(), hotel.getDailyValue(), hotel.getRealDailyValue(), hotel.getPhotos(),
         new AddressHotelCardProjection(hotel.getAddress()),(hotel.getPromotion()!=null) ? new PromotionCardProjection(hotel.getPromotion()) : null);
    }

    public HotelDatesCardProjection(String name, boolean inPromotion, BigDecimal dailyValue, BigDecimal realDailyValue, List<byte[]> photos, AddressHotelCardProjection address, PromotionCardProjection promotion) {
        this.name = name;
        this.inPromotion = inPromotion;
        this.dailyValue = dailyValue;
        this.photos = photos;
        this.realDailyValue = realDailyValue;
        this.address = address;
        this.promotion = promotion;
    }


}
