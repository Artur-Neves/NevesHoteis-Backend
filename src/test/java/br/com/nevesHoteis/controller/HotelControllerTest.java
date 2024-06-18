package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.domain.Address;

import br.com.nevesHoteis.domain.Dto.HotelCompleteDto;
import br.com.nevesHoteis.domain.Dto.HotelDto;
import br.com.nevesHoteis.domain.Hotel;
import br.com.nevesHoteis.service.HotelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

    @Test
    @DisplayName("Testando o find all dos hoteis")
    void test01() throws Exception {
        Page<Hotel> hotelPage = new PageImpl<>(List.of(randomHotel()));
        given(service.findAll(any(Pageable.class))).willReturn(hotelPage);
        mockMvc.perform(get("/hotel"))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(pageHotelCompleteDto.write(hotelPage.map(HotelCompleteDto::new)).getJson()));
    }
    @WithMockUser
    @Test
    @DisplayName("Testando o salvamento da entidade hotel")
    void test02() throws Exception {
        Hotel hotel = randomHotel();
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
    @DisplayName("Selecionando apenas uma entidade ")
    void test03() throws Exception {
        Hotel hotel = randomHotel();
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
    @WithMockUser
    @Test
    @DisplayName("Testando a atualização da entidade hotel")
    void test05() throws Exception {
        Hotel hotel = randomHotel();
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
    @WithMockUser
    @Test
    @DisplayName("Testando o delete da entidade hotel")
    void test06() throws Exception {
        Hotel hotel = randomHotel();
        mockMvc.perform(delete("/hotel/"+hotel.getId()))
                .andExpectAll(status().isNoContent());
        then(service).should().delete(anyLong())   ;
    }

    private Hotel randomHotel(){
        return new Hotel(1L, "Hotel fiveStars", LocalDateTime.now().plusWeeks(2), new BigDecimal(35), randomAddress());
    }
    private Address randomAddress(){
        return new Address(1L, "45502-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
    }
}