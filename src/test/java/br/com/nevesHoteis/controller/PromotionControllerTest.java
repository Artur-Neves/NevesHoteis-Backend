package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.controller.dto.PromotionCompleteDto;
import br.com.nevesHoteis.controller.dto.PromotionDto;
import br.com.nevesHoteis.controller.dto.PromotionUpdateDto;
import br.com.nevesHoteis.controller.dto.hotel.HotelCompleteDto;
import br.com.nevesHoteis.domain.Hotel;
import br.com.nevesHoteis.domain.Promotion;
import br.com.nevesHoteis.service.PromotionService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static br.com.nevesHoteis.Fixture.buildHotel;
import static br.com.nevesHoteis.Fixture.buildPromotion;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WithMockUser(authorities = "EMPLOYEE")
class PromotionControllerTest extends BaseControllerTest<PromotionService> {
    @Autowired
    JacksonTester<PromotionCompleteDto> promotionCompleteDtoJacksonTester;
    @Autowired
    JacksonTester<PromotionDto> promotionDtoJacksonTester;
    @Autowired
    JacksonTester<PromotionUpdateDto> promotionUpdateDtoJacksonTester;
    @DisplayName("Testando o selecionamento de uma Promotion")
    @Test()
    void test01() throws Exception {
       Promotion promotion = buildPromotion();
        when(service.findById(eq(promotion.getId()))).thenReturn(promotion);
        mockMvc.perform(get("/hotel/promotion/"+promotion.getId()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                    content().json(promotionCompleteDtoJacksonTester.write( new PromotionCompleteDto(promotion)).getJson()));
    }

    @DisplayName("Testando a persistência da entidade Promotion")
    @Test()
    void test02() throws Exception {
       Promotion promotion = buildPromotion();
        when(service.save(any())).thenReturn(promotion);
        mockMvc.perform(post("/hotel/promotion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(promotionDtoJacksonTester.write(new PromotionDto(promotion)).getJson()))
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(promotionCompleteDtoJacksonTester.write(new PromotionCompleteDto(promotion)).getJson())
                        );
    }

    @DisplayName("Testando a atualização da entidade Promotion")
    @Test()
    void teste03() throws Exception {
       Promotion promotion = buildPromotion();
        when(service.extendDuration(any(), anyLong())).thenReturn(promotion);
        mockMvc.perform(put("/hotel/promotion/"+promotion.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(promotionUpdateDtoJacksonTester.write(new PromotionUpdateDto(promotion)).getJson()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(promotionCompleteDtoJacksonTester.write(new PromotionCompleteDto(promotion)).getJson()));
    }

    @DisplayName("Testando a exclusão da entidade Promotion")
    @Test
    void test04() throws Exception {
       Promotion promotion = buildPromotion();
        mockMvc.perform(delete("/hotel/promotion/"+promotion.getId()))
                .andExpectAll(status().isNoContent());
        then(service).should().remove(promotion.getId());
    }

    @DisplayName("Testando erro ao tentar excluir a entidade Promotion")
    @Test
    void test05() throws Exception {
       Promotion promotion = buildPromotion();
        mockMvc.perform(delete("/hotel/promotion/"))
                .andExpectAll(status().isNotFound());
    }

}