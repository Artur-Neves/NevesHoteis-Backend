package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.domain.Address;

import br.com.nevesHoteis.controller.Dto.HotelCompleteDto;
import br.com.nevesHoteis.controller.Dto.HotelDto;
import br.com.nevesHoteis.domain.Hotel;
import br.com.nevesHoteis.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(authorities = "EMPLOYEE")
class HotelControllerTest extends BaseControllerTest<HotelService> {
    @Mock
    private Pageable pageable;
    @Autowired
    private JacksonTester<HotelDto> hotelDtoJacksonTester;
    @Autowired
    private JacksonTester<HotelCompleteDto> hotelCompleteDtoJacksonTester;
    @Autowired
    private JacksonTester<Page<HotelCompleteDto>> pageHotelCompleteDto;
    @Mock
    private HotelDto hotelDto;
    @Mock
    private HotelCompleteDto completeDto;
    @Mock
    private Hotel hotelMock;
    private Hotel hotel;
    private Address address;

    @BeforeEach
    void setUp() {
        address =new Address( "45502-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
        hotel = new Hotel(1L, "Hotel fiveStars", LocalDate.now().plusWeeks(2), new BigDecimal(35), address);
    }
    @WithMockUser
    @Test
    @DisplayName("Testando o selecionamento de todos os hoteis")
    void test01() throws Exception {
        Page<Hotel> hotelPage = new PageImpl<>(List.of(hotel));
        given(service.findAll(any(Pageable.class))).willReturn(hotelPage);
        mockMvc.perform(get("/hotel"))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(pageHotelCompleteDto.write(hotelPage.map(HotelCompleteDto::new)).getJson()));
    }

    @Test
    @DisplayName("Testando o salvamento da entidade hotel")
    void test02() throws Exception {
        given(service.save(any())).willReturn(hotel);
        hotelDto = new HotelDto(hotel);
        mockMvc.perform(post("/hotel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(hotelDtoJacksonTester.write(hotelDto).getJson())
                )
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(hotelCompleteDtoJacksonTester.write(new HotelCompleteDto(hotel)).getJson()));
    }
    @Test
    @DisplayName("Testando o selecionamento de apenas uma entidade hotel ")
    void test03() throws Exception {
        given(service.findById(eq(hotel.getId()))).willReturn(hotel);
        mockMvc.perform(get("/hotel/"+hotel.getId())
                )
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(hotelCompleteDtoJacksonTester.write(new HotelCompleteDto(hotel)).getJson()));
    }
    @Test
    @DisplayName("Retornando erro ao testar uma entidade que não existe ")
    void test04() throws Exception {
        mockMvc.perform(get("/hotel/"))
                .andExpectAll(status().isNotFound());
    }
    @Test
    @DisplayName("Testando a atualização da entidade hotel")
    void test05() throws Exception {
        hotelDto = new HotelDto(hotel);
        given(service.update(anyLong(), any(Hotel.class))).willReturn(hotel);
        mockMvc.perform(put("/hotel/"+hotel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(hotelDtoJacksonTester.write(hotelDto).getJson())
                )
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(hotelCompleteDtoJacksonTester.write(new HotelCompleteDto(hotel)).getJson()));
    }
    @Test
    @DisplayName("Testando a exclusão da entidade hotel")
    void test06() throws Exception {
        mockMvc.perform(delete("/hotel/"+hotel.getId()))
                .andExpectAll(status().isNoContent());
        then(service).should().delete(anyLong())   ;
    }
}