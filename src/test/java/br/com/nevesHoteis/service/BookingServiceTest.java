package br.com.nevesHoteis.service;

import br.com.nevesHoteis.controller.dto.BookingDto;
import br.com.nevesHoteis.controller.dto.BookingUpdateDto;
import br.com.nevesHoteis.domain.Booking;
import br.com.nevesHoteis.domain.Hotel;
import br.com.nevesHoteis.domain.Role;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.repository.BookingRepository;
import br.com.nevesHoteis.repository.HotelRepository;
import br.com.nevesHoteis.service.validation.booking.*;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static br.com.nevesHoteis.Fixture.buildBooking;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @InjectMocks
    private BookingService service;
    @Mock
    private BookingRepository repository;
    @Mock
    private SimpleUserService simpleUserService;
    @Mock
    private HotelService hotelService;
    @Mock
    private Hotel hotelMock;
    @Mock
    private SimpleUser simpleUserMock;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private List<ValidateBooking> validateBookings = new ArrayList<>();
    @Mock
    private ValidateCorrectCancel validateCorrectCancel;
    @Mock
    private ValidateCorrectUser validateCorrectUser;
    @Mock
    private ValidateDatesFree validateDatesFree;
    @Mock
    private ValidateEndDate validateEndDate;
    @Test
    @DisplayName("Testando o salvamento da entidade")
    void test01(){
        service.setValidateBooking(Arrays.asList(validateCorrectCancel, validateCorrectUser, validateDatesFree, validateEndDate));

        Booking booking = buildBooking();
        when(hotelService.findById(eq(booking.getId()))).thenReturn(booking.getHotel());

        when(repository.save(any(Booking.class))).thenReturn(booking);

        assertEquals(booking, service.save(new BookingDto(booking)));
        then(validateCorrectCancel).should().validateBooking(any(Booking.class));
        verify(simpleUserService).findByUserLogin(any());
        verify(validateCorrectUser).validateBooking(any());
        verify(validateDatesFree).validateBooking(any());
        verify(validateEndDate).validateBooking(any());
    }
    @Test
    @DisplayName("Testando o selecionamento de todas as entidade")
    void test02(){
        Page<Booking> page = new PageImpl<>(Arrays.asList(buildBooking()));
        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        assertEquals(page, service.findAll(mock(Pageable.class)));
    }
    @Test
    @DisplayName("Testando a atualizaçãp  da entidade")
    void test03(){
        Page<Booking> page = new PageImpl<>(Arrays.asList(buildBooking()));
        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        assertEquals(page, service.findAll(mock(Pageable.class)));
    }
    @Test
    @DisplayName("Testando o listamento da entidade pelo id")
    void teste04(){
        Booking booking = buildBooking();
        when(repository.findById(anyLong())).thenReturn(Optional.of(booking));
        assertEquals(booking, service.findById(1L));
    }
    @Test
    @DisplayName("Testando o retorno de uma exceção ao tentar listar pelo id")
    void teste05(){
        Booking booking = buildBooking();
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()-> service.findById(1l));
    }
    @Test
    @DisplayName("Testando a remoção da determinada entidade")
    void teste06(){
        Booking booking = buildBooking();
        when(repository.findById(anyLong())).thenReturn(Optional.of(booking));
        assertDoesNotThrow(()->service.delete(1L));
        then(repository).should().delete(eq(booking));
    }

    @Test
    @DisplayName("Testando a atualização da entidade")
    void teste07(){
        service.setValidateBooking(Arrays.asList(validateCorrectCancel, validateCorrectUser, validateDatesFree, validateEndDate));
        Booking booking = mock();
        when(repository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(repository.save(any(Booking.class))).thenReturn(booking);
        assertEquals(booking, service.updateDates(new BookingUpdateDto(booking), booking.getId()));
        then(validateCorrectCancel).should().validateBooking(any(Booking.class));
        verify(booking).merge(any(), any());
        verify(validateCorrectUser).validateBooking(any());
        verify(validateDatesFree).validateBooking(any());
        verify(validateEndDate).validateBooking(any());
    }
    @Test
    @DisplayName("Testando a busca do usuário pela autenticação")
    void teste08(){
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = new UsernamePasswordAuthenticationToken("artur.diamante@gmail.com", null, List.of(Role.USER));
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
        when(simpleUserService.findByUserLogin(eq("artur.diamante@gmail.com"))).thenReturn(simpleUserMock);
        assertEquals(simpleUserMock, service.getSimpleUserByAuthentication());
    }
}