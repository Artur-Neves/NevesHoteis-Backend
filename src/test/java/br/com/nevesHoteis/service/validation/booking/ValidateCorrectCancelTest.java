package br.com.nevesHoteis.service.validation.booking;

import br.com.nevesHoteis.domain.Booking;
import br.com.nevesHoteis.infra.exeption.ValidateBookingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;

import static br.com.nevesHoteis.Fixture.buildBooking;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ValidateCorrectCancelTest {
    @InjectMocks
    private ValidateCorrectCancel correctCancel;
    @Test
    @DisplayName("Deveria validadar corretamente")
    void teste01(){
        Booking booking = buildBooking();
        assertDoesNotThrow(()->correctCancel.validateBooking(booking));
        booking.merge(LocalDate.now(), LocalDate.now().plusDays(2));
        assertDoesNotThrow(()->correctCancel.validateBooking(booking));
    }
    @Test
    @DisplayName("Não deveria passar")
    void teste02() throws IllegalAccessException {
        Booking booking = buildBooking();
        Field field = Arrays.stream(booking.getClass().getDeclaredFields()).filter(t -> t.getName().equals("cancellationDeadline")).findFirst().get();
        field.setAccessible(true);
        field.set(booking, LocalDate.now().plusDays(2));
        assertThrows(ValidateBookingException.class, ()->correctCancel.validateBooking(booking));
        field.set(booking, LocalDate.now());
        assertThrows(ValidateBookingException.class, ()->correctCancel.validateBooking(booking));
        field.set(booking, LocalDate.now().minusDays(1));
        assertThrows(ValidateBookingException.class, ()->correctCancel.validateBooking(booking));
        field.set(booking, LocalDate.now().minusDays(3));
        assertThrows(ValidateBookingException.class, ()->correctCancel.validateBooking(booking));

    }

}