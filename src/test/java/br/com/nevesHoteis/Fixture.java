package br.com.nevesHoteis;

import br.com.nevesHoteis.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    static Hotel buildHotel(){
        return new Hotel(1L, "Hotel fiveStars", new BigDecimal(35), buildAddress());
    }
}
