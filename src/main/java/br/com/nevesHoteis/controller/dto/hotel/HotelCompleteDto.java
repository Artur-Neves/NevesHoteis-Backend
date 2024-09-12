package br.com.nevesHoteis.controller.dto.hotel;

import br.com.nevesHoteis.controller.dto.address.AddressCompleteDto;
import br.com.nevesHoteis.domain.Hotel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record HotelCompleteDto (
         Long id,
         String name,
         BigDecimal dailyValue,
         BigDecimal realDailyValue,
         List<byte[]> photos,
         Boolean inPromotion,
         AddressCompleteDto address
){
    public HotelCompleteDto(Hotel hotel) {
        this(hotel.getId(), hotel.getName()
        , hotel.getDailyValue(), hotel.getRealDailyValue(), hotel.getPhotos(), hotel.isInPromotion(),new AddressCompleteDto( hotel.getAddress()));
    }
}
