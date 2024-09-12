package br.com.nevesHoteis.service.bookings;

import br.com.nevesHoteis.domain.Promotion;
import br.com.nevesHoteis.repository.PromotionRepository;
import org.hibernate.Internal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingPromotionValidateTest {
    @InjectMocks
    private BookingPromotionValidate service;
    @Mock
    private PromotionRepository repository;
    @Mock
    private Promotion promotionMock1;
    @Mock
    private Promotion promotionMock2;


    @DisplayName("Testando a tarefa de atualizar o estado de uma promoção")
    @Test
    void test01(){
        when(repository.findByDeletedFalseAndEndDateBefore(any())).thenReturn(List.of(promotionMock1, promotionMock2));
        service.bookingUpdateValidate();
        then(promotionMock1).should().cancelPromotion();
        then(promotionMock2).should().cancelPromotion();
    }

}