package br.com.nevesHoteis;

import br.com.nevesHoteis.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Fixture {
    public static Booking buildBooking(){
        return new Booking(1L, LocalDate.now()
                , LocalDate.now().plusDays(3), buildSimpleUser(), buildHotel());
    }
    static SimpleUser buildSimpleUser(){
        return new SimpleUser(1L, "Artur", LocalDate.now().plusYears(-18), "123.456.890-90", "73988888888", buildAddress(), buildUser());
    }
    static User buildUser(){
        return new User(1L, "artur@gmail.com",true , "Ar606060", Role.USER, null);
    }
    static Address buildAddress(){
        return new Address( "76854-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
    }
    public static Hotel buildHotel(){
        Hotel hotel = new Hotel(1L, "Hotel fiveStars", new BigDecimal(35), buildAddress());
//        hotel.getListPromotion().add(new Promotion( 1L, BigDecimal.valueOf(10), LocalDateTime.now().plusHours(1), LocalDateTime.now().plusDays(4), hotel));
        return hotel;
    }
    public static Promotion buildPromotion(){
        return new Promotion(1L, BigDecimal.valueOf(10), LocalDateTime.now().plusHours(1), LocalDateTime.now().plusDays(4), buildHotel());
    }
}
