package br.com.nevesHoteis.domain;

import br.com.nevesHoteis.controller.dto.PromotionDto;
import br.com.nevesHoteis.controller.dto.PromotionUpdateDto;
import br.com.nevesHoteis.infra.exeption.ValidatePromotionException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "hotel_promotion")
@Getter
@SoftDelete
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal discount;
    private int percentageDiscount;
    private LocalDateTime startDate;
    @Setter
    private LocalDateTime endDate;
    @Column(insertable=false, updatable=false)
    private boolean deleted = Boolean.FALSE;
    @ManyToOne()
    @Setter
    private Hotel hotel;

    public Promotion(Long id, BigDecimal discount, LocalDateTime startDate, LocalDateTime endDate, Hotel hotel) {
        this.id = id;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hotel = hotel;
        configurationsPromotion();
    }
    public Promotion(BigDecimal discount, LocalDateTime startDate, LocalDateTime endDate, Hotel hotel) {
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hotel = hotel;
        configurationsPromotion();
    }

    public Promotion(PromotionDto dto) {
        this.discount = dto.discount();
        this.startDate =dto.startDate();
        this.endDate = dto.endDate();
        this.hotel = new Hotel(dto.idHotel());
        configurationsPromotion();
    }

    public Promotion(PromotionUpdateDto dto){
        this.discount = dto.discount();
        this.endDate = dto.endDate();
    }

    private void updateStatePromotionHotel(){
        boolean existPromotionInHotel= !hotel.isInPromotion() || hotel.getPromotion()!=null;
        if(existPromotionInHotel){
            hotel.setInPromotion(true);
        }
        else{
            throw new ValidatePromotionException("Promoção do Hotel", "Um hotel só pode ter uma promoção por vez!");
        }
    }

    private void convertInPercentageDiscount(){
        if(discount.compareTo(hotel.getDailyValue()) <= 0){
            this.percentageDiscount = (int) Math.round(100-this.discount.divide(hotel.getDailyValue(), MathContext.DECIMAL32).doubleValue()*100);
        }
        else {
            throw new ValidatePromotionException("Valor da inválido da promoção", "O valor da promoção deve ser maior que o valor da diária do hotel em que encontra-se");
        }
    }

    private void realDailyValueCalculation(){
        BigDecimal realValue = this.hotel.getDailyValue().subtract(this.discount);
        this.hotel.setRealDailyValue(realValue);
    }

    private void configurationsPromotion(){
        realDailyValueCalculation();
        convertInPercentageDiscount();
        updateStatePromotionHotel();
    }

    public void cancelPromotion(){
        if (!this.isDeleted()) {
            endDate = LocalDateTime.now();
            deleted = true;
            this.getHotel().setRealDailyValue(this.getHotel().getDailyValue());
            hotel.setInPromotion(false);
        }
        else {
            throw new ValidatePromotionException("Promoção já esta cancelada", "Uma promoção não pode ser cancelada duas vezes");
        }
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "id=" + id +
                ", discount=" + discount +
                ", percentageDiscount=" + percentageDiscount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", deleted=" + deleted +
                ", hotel=" + hotel +
                '}';
    }

    public void merge(Promotion p) {
        if (this.deleted) {
            updateStatePromotionHotel();
            this.deleted = false;
        }
        this.discount = p.getDiscount();
        this.endDate = p.getEndDate();
        realDailyValueCalculation();
        convertInPercentageDiscount();
    }
}
