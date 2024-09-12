package br.com.nevesHoteis.service;

import br.com.nevesHoteis.controller.dto.PromotionDto;
import br.com.nevesHoteis.domain.Hotel;
import br.com.nevesHoteis.domain.Promotion;
import br.com.nevesHoteis.repository.PromotionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PromotionService {
    @Autowired
    private PromotionRepository repository;
    @Autowired
    private HotelService hotelService;
    public Promotion save(PromotionDto promotionDto){
        Hotel hotel = hotelService.findById(promotionDto.idHotel());
        Promotion promotion = new Promotion(promotionDto.discount(), promotionDto.startDate(),  promotionDto.endDate(), hotel);
        return repository.save(promotion);
    }
    public Promotion findById(Long id){
        return repository.findById(id).orElseThrow( ()->new EntityNotFoundException("Não existe uma entidade Promotion com este identificador."));
    }
    @Transactional
    public void remove(Long id) {
        Promotion promotion = findById(id);
        promotion.cancelPromotion();
    }
    @Transactional
    public Promotion extendDuration(Promotion promotionUpdateDto, Long id) {
        Promotion promotion = findById(id);
        promotion.merge(promotionUpdateDto);
        return promotion;
    }
}
