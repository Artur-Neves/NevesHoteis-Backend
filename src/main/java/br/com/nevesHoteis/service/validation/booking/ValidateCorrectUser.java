package br.com.nevesHoteis.service.validation.booking;

import br.com.nevesHoteis.domain.Booking;
import br.com.nevesHoteis.infra.exeption.ValidateBookingException;
import br.com.nevesHoteis.service.SimpleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ValidateCorrectUser implements ValidateBooking{
    @Override
    public void validateBooking(Booking booking) {
        String login = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!login.equals(booking.getSimpleUser().getUser().getLogin())){
            throw new ValidateBookingException("Usuário", "O usuário que esta tentando realizar a reserva não é o mesmo que esta logado!");
        };
    }
}
