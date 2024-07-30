package br.com.nevesHoteis.service.validation.booking;

import br.com.nevesHoteis.domain.Booking;
import br.com.nevesHoteis.infra.exeption.ValidateBookingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static br.com.nevesHoteis.Fixture.buildBooking;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ValidateEndDateTest {
    @InjectMocks
    private ValidateEndDate validateEndDate;

    @Test
    @DisplayName("Deveria validar quando a data final da estadia é pelo menos um dia depois da entrada")
    void test01() {
        Booking booking = buildBooking();
        assertDoesNotThrow(()-> validateEndDate.validateBooking(booking));
        booking.merge(LocalDate.now(), LocalDate.now().plusDays(1));
        assertDoesNotThrow(()-> validateEndDate.validateBooking(booking));
    }
    @Test
    @DisplayName("Deveria retornar uma Exception quando a data final da estadia não é de pelo menos um dia depois da entrada")
    void test02() {
        Booking booking = buildBooking();
        booking.merge(LocalDate.now(), LocalDate.now());
        assertThrows(ValidateBookingException.class, ()-> validateEndDate.validateBooking(booking));
        booking.merge(LocalDate.now(), LocalDate.now().minusDays(2));
        assertThrows(ValidateBookingException.class,()-> validateEndDate.validateBooking(booking));
    }
}