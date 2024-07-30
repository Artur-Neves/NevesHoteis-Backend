package br.com.nevesHoteis.service.validation.booking;

import br.com.nevesHoteis.domain.Booking;
import br.com.nevesHoteis.infra.exeption.ValidateBookingException;
import org.springframework.stereotype.Component;

@Component
public class ValidateCorrectCancel implements ValidateBooking{
    @Override
    public void validateBooking(Booking booking) {
        if (!booking.getCancellationDeadline().isEqual(booking.getStartDate().minusDays(2))) {
            throw new ValidateBookingException("Data de cancelamento", "A data de cancelamento deve ser 2 dias antes da data de início");
        }
    }
}
