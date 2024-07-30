package br.com.nevesHoteis.service.validation.booking;

import br.com.nevesHoteis.domain.Booking;
import br.com.nevesHoteis.infra.exeption.ValidateBookingException;
import br.com.nevesHoteis.repository.BookingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;

import static br.com.nevesHoteis.Fixture.buildBooking;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateDatesFreeTest {
    @InjectMocks
    private ValidateDatesFree validate;
    @Mock
    private BookingRepository bookingRepository;

    @Test
    @DisplayName("Deveria Validar quando não há reservas nos dias escolhidos")
    void test01(){
        Booking booking = buildBooking();
        when(bookingRepository.existsDateBetweenStartDateOrEndDate(booking.getStartDate(), booking.getEndDate(), booking.getId())).thenReturn(false);
        assertDoesNotThrow(()->validate.validateBooking(booking));
    }
    @Test
    @DisplayName("Deveria retornar uma excption quando há reservas nos dias escolhidos")
    void test02(){
        Booking booking = buildBooking();
        when(bookingRepository.existsDateBetweenStartDateOrEndDate(booking.getStartDate(), booking.getEndDate(), booking.getId())).thenReturn(true);
        assertThrows(ValidateBookingException.class, ()->validate.validateBooking(booking));
    }

}