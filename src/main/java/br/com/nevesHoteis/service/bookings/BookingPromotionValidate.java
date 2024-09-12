package br.com.nevesHoteis.service.bookings;

import br.com.nevesHoteis.domain.Promotion;
import br.com.nevesHoteis.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BookingPromotionValidate {
    @Autowired
    private PromotionRepository promotionRepository;

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void bookingUpdateValidate(){
        List<Promotion> listPromotion = promotionRepository.findByDeletedFalseAndEndDateBefore  (LocalDateTime.now());
        listPromotion.forEach(Promotion::cancelPromotion);
    }
}
