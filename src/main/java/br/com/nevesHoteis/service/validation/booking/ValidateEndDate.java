package br.com.nevesHoteis.service.validation.booking;

import br.com.nevesHoteis.domain.Booking;
import br.com.nevesHoteis.infra.exeption.ValidateBookingException;
import org.springframework.stereotype.Component;

@Component
public class ValidateEndDate implements  ValidateBooking{
    @Override
    public void validateBooking(Booking booking) {
        if (!booking.getEndDate().isAfter(booking.getStartDate()))
            throw new ValidateBookingException("Data de saída", "A data de saída deve ser posterior a data de entrada.");

    }
}
