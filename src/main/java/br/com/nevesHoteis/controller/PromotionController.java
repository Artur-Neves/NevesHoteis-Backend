package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.controller.dto.PromotionCompleteDto;
import br.com.nevesHoteis.controller.dto.PromotionDto;
import br.com.nevesHoteis.controller.dto.PromotionUpdateDto;
import br.com.nevesHoteis.domain.Promotion;
import br.com.nevesHoteis.service.PromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.net.URI;

@RestController
@RequestMapping("/hotel/promotion")
public class PromotionController {
    @Autowired
    private PromotionService service;

    @PostMapping()
    public ResponseEntity<PromotionCompleteDto> save(@RequestBody @Valid PromotionDto promotionDto, UriComponentsBuilder uriComponentsBuilder){
        Promotion promotion = service.save(promotionDto);
        URI uri = uriComponentsBuilder.path("/hotel/promotion/{id}").buildAndExpand(promotion.getId()).toUri();
        return ResponseEntity.created(uri).body(new PromotionCompleteDto(promotion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        service.remove(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromotionCompleteDto> extendDuration(@RequestBody @Valid PromotionUpdateDto promotionUpdateDto, @PathVariable Long id){
        return ResponseEntity.ok(new PromotionCompleteDto(service.extendDuration(new Promotion(promotionUpdateDto), id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionCompleteDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(new PromotionCompleteDto(service.findById(id)));
    }
}
