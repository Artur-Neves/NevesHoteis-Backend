package br.com.nevesHoteis.service.validation.booking;

import br.com.nevesHoteis.domain.Booking;
import br.com.nevesHoteis.domain.Role;
import br.com.nevesHoteis.infra.exeption.ValidateBookingException;
import br.com.nevesHoteis.service.SimpleUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static br.com.nevesHoteis.Fixture.buildBooking;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateCorrectUserTest {
    @InjectMocks
    private  ValidateCorrectUser validateCorrectUser;
    @Mock
    private SecurityContext securityContext;
    @Test()
    @DisplayName("Deveria validar quando o usuário logado é o mesmo presente na entidade booking")
    void teste01(){
        Booking booking = buildBooking();
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = new UsernamePasswordAuthenticationToken(booking.getSimpleUser().getUser().getLogin(), null, List.of(Role.USER));
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
        assertDoesNotThrow(()->validateCorrectUser.validateBooking(buildBooking()));
    }
    @Test()
    @DisplayName("Lançar exception quando o usuário logado não é o mesmo presente na entidade booking")
    void teste02(){
        Booking booking = buildBooking();
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = new UsernamePasswordAuthenticationToken("exemploFake@gamil.com", null, List.of(Role.USER));
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
        assertThrows(ValidateBookingException.class, ()->validateCorrectUser.validateBooking(buildBooking()));
    }

}