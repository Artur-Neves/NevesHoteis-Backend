package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.controller.dto.BookingCompleteDto;
import br.com.nevesHoteis.controller.dto.BookingDto;
import br.com.nevesHoteis.controller.dto.BookingUpdateDto;
import br.com.nevesHoteis.controller.dto.hotel.HotelCompleteDto;
import br.com.nevesHoteis.controller.dto.hotel.HotelDto;
import br.com.nevesHoteis.domain.*;
import br.com.nevesHoteis.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WithMockUser(authorities = "USER")
public class BookingControllerTest extends BaseControllerTest<BookingService>{
    @Mock
    private Pageable pageable;
    @Autowired
    private JacksonTester<BookingDto> bookingDtoJacksonTester;
    @Autowired
    private JacksonTester<BookingCompleteDto> bookingCompleteDtoJacksonTester;
    @Autowired
    private JacksonTester<Page<BookingCompleteDto>> pageBrookingCompleteDto;
    @Autowired
    private JacksonTester<BookingUpdateDto> bookingUpdateDtoJacksonTester;
    @Mock
    private BookingDto bookingDto;
    @Mock
    private BookingCompleteDto bookingCompleteDto;
    @Mock
    private Booking hotelMock;
    private Hotel hotel;
    private SimpleUser simpleUser;
    private Booking booking;

    @BeforeEach
    void setUp() {
        User user = new User(1L, "artur@gmail.com",true , "Ar606060", Role.USER, null);
        Address address = new Address( "76854-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
        hotel = new Hotel(1L, "Hotel fiveStars", new BigDecimal(35), address);
        simpleUser = new SimpleUser(1L, "Artur", LocalDate.now().plusYears(-18), "123.456.890-90", "73988888888", address, user);
        booking = new Booking(1L, LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3), simpleUser, hotel);
    }
    @WithMockUser(authorities = "ADMIN")
    @Test
    @DisplayName("Testando o selecionamento de todas as reservas")
    void test01() throws Exception {
        Page<Booking> bookingPage = new PageImpl<>(List.of(booking));
        given(service.findAll(any(Pageable.class))).willReturn(bookingPage);
        mockMvc.perform(get("/booking"))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(pageBrookingCompleteDto.write(bookingPage.map(BookingCompleteDto::new)).getJson()));
    }

    @Test
    @DisplayName("Testando o salvamento da entidade booking")
    void test02() throws Exception {
        given(service.save(any())).willReturn(booking);
        mockMvc.perform(post("/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingDtoJacksonTester.write(new BookingDto(booking)).getJson()))
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(bookingCompleteDtoJacksonTester.write(new BookingCompleteDto(booking)).getJson()));
    }
    @Test
    @DisplayName("Testando o selecionamento de apenas uma entidade booking ")
    void test03() throws Exception {
        given(service.findById(eq(booking.getId()))).willReturn(booking);
        mockMvc.perform(get("/booking/"+booking.getId())
                )
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(bookingCompleteDtoJacksonTester.write(new BookingCompleteDto(booking)).getJson()));
    }
    @Test
    @DisplayName("Retornando erro ao testar uma entidade que não existe ")
    void test04() throws Exception {
        mockMvc.perform(get("/booking/"))
                .andExpectAll(status().isNotFound());
    }
    @Test
    @DisplayName("Testando a atualização da entidade booking")
    void test05() throws Exception {
        BookingUpdateDto bookingUpdateDto = new BookingUpdateDto(booking.getStartDate(), booking.getEndDate());
        given(service.updateDates(eq(bookingUpdateDto),  anyLong())).willReturn(booking);
        mockMvc.perform(put( "/booking/"+booking.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingUpdateDtoJacksonTester.write(bookingUpdateDto).getJson()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(bookingCompleteDtoJacksonTester.write(new BookingCompleteDto(booking)).getJson()));
    }
    @Test
    @DisplayName("Testando a exclusão da entidade booking")
    void test06() throws Exception {
        mockMvc.perform(delete("/booking/"+booking.getId()))
                .andExpectAll(status().isNoContent());
        then(service).should().delete(anyLong())   ;
    }

}
