package br.com.nevesHoteis.service;

import br.com.nevesHoteis.controller.dto.PromotionDto;
import br.com.nevesHoteis.domain.Promotion;
import br.com.nevesHoteis.repository.PromotionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static br.com.nevesHoteis.Fixture.buildPromotion;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PromotionServiceTest {
    @InjectMocks
    private PromotionService service;
    @Mock
    private PromotionRepository repository;
    @Mock
    private HotelService hotelService;
    private Promotion promotion = buildPromotion();
    @Mock
    private Promotion promotionMock;
    @DisplayName("Testando a persistência da entidade promotion")
    @Test
    void test01(){
        when(hotelService.findById(promotion.getHotel().getId())).thenReturn(promotion.getHotel());
        when(repository.save(any())).thenReturn(promotion);
        assertEquals(service.save(new PromotionDto(promotion)), promotion);
    }

    @DisplayName("Testando a busca correta por id de uma promotion")
    @Test
    void test02(){
        when(repository.findById(1L)).thenReturn(Optional.of(promotion));
        assertEquals(service.findById(1L), promotion);
    }

    @DisplayName("Testando a busca correta por id de uma promotion")
    @Test
    void test03(){
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()->service.findById(1L));
    }

    @DisplayName("Testando o cancelamento de um promotion")
    @Test
    void test04(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(promotionMock));
        assertDoesNotThrow(()->service.remove(1L));
        then(promotionMock).should().cancelPromotion();
    }

    @DisplayName("Testando a extensão da duração da entidade promotion")
    @Test
    void test05(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(promotionMock));
        assertEquals(service.extendDuration(promotion, 1L), promotionMock);
        then(promotionMock).should().merge(promotion);
    }

}