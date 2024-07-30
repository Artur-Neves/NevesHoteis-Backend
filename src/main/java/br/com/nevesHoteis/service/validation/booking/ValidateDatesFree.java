package br.com.nevesHoteis.service.validation.booking;

import br.com.nevesHoteis.domain.Booking;
import br.com.nevesHoteis.infra.exeption.ValidateBookingException;
import br.com.nevesHoteis.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateDatesFree implements ValidateBooking {
    @Autowired
    private BookingRepository repository;
    @Override
    public void validateBooking(Booking booking) {
        boolean bool =  repository.existsDateBetweenStartDateOrEndDate(booking.getStartDate(), booking.getEndDate(), booking.getId());
        if (bool)
            throw new ValidateBookingException("Dates", "O período escolhido não esta livre para hospedagem");

    }
}
