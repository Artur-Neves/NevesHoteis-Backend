package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.domain.Address;

import br.com.nevesHoteis.controller.dto.hotel.HotelCompleteDto;
import br.com.nevesHoteis.controller.dto.hotel.HotelDto;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static br.com.nevesHoteis.infra.utils.Conversions.convertMultiPartFileInByte;
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
        hotel = new Hotel(1L, "Hotel fiveStars", new BigDecimal(35), address);
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
        mockMvc.perform(creatingFormData(HttpMethod.POST, "/hotel"))
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
        mockMvc.perform(creatingFormData(HttpMethod.PUT, "/hotel/"+hotel.getId()))
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
    MockMultipartHttpServletRequestBuilder creatingFormData(HttpMethod method, String endpoint){
        MockMultipartFile multipartFile = new MockMultipartFile("file", "image.jpg",
                "Image/jpg", "Spring Framework".getBytes());
        List<MultipartFile> multipartFileList = List.of(multipartFile);
        return   (MockMultipartHttpServletRequestBuilder) multipart(method, endpoint)
                .file("photos", convertMultiPartFileInByte( multipartFile))
                .file("photos", convertMultiPartFileInByte( multipartFile))
                .file("photos", convertMultiPartFileInByte( multipartFile))
                .file("photos", convertMultiPartFileInByte( multipartFile))
                .param("name", hotel.getName())
                .param("dailyValue", hotel.getDailyValue().toString())
                .param("address.cep", hotel.getAddress().getCep())
                .param("address.state", hotel.getAddress().getState())
                .param("address.city", hotel.getAddress().getCity())
                .param("address.neighborhood", hotel.getAddress().getNeighborhood())
                .param("address.propertyLocation", hotel.getAddress().getPropertyLocation());
    }
}