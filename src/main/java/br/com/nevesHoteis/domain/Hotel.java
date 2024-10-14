package br.com.nevesHoteis.domain;


import br.com.nevesHoteis.controller.dto.hotel.HotelDto;
import br.com.nevesHoteis.infra.exeption.ValidateHotelException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static br.com.nevesHoteis.infra.utils.Conversions.convertListMultiPartFileInListByte;

@Entity
@Getter
@NoArgsConstructor
public class Hotel {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal dailyValue;
    @Setter
    private BigDecimal realDailyValue;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "hotel")
    private List<Booking> bookings = new ArrayList<>();
    @Setter
    @Column(columnDefinition = "boolean default false")
    private boolean inPromotion;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Promotion> promotion = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "hotel_photos", joinColumns = @JoinColumn(name = "hotel_id"))
    private List<byte[]> photos = new ArrayList<>();
    @ColumnDefault(value = "0")
    private int countVisitors = 0;

    public Hotel(Long id) {
        this.id = id;
    }

    public Hotel(HotelDto dto){
       this.name= dto.getName();
       this.dailyValue = dto.getDailyValue();
       this.realDailyValue = this.dailyValue;
       this.address= new Address(dto.getAddress());
       this.photos = convertListMultiPartFileInListByte(dto.getPhotos());
    }

    public Hotel merge(Hotel dto) {
        if(this.inPromotion){
            throw new ValidateHotelException("Hotel em promoção", "Por motivos de segurança, você não pode alterar um hotel que esta em promoção!");
        }
        this.name= dto.getName();
        this.dailyValue = dto.getDailyValue();
        this.realDailyValue = this.dailyValue;
        this.address= dto.getAddress();
        this.photos = dto.getPhotos();
        return this;
    }

    public Hotel(Long id, String name, BigDecimal dailyValue, Address address) {
        this.id = id;
        this.name = name;
        this.dailyValue = dailyValue;
        this.realDailyValue = this.dailyValue;
        this.address = address;
    }

    public void setDailyValue(BigDecimal dailyValue) {
        this.dailyValue = dailyValue;
        this.realDailyValue = dailyValue;
    }

    public Promotion getPromotion(){
        return promotion.stream().filter(p -> !p.isDeleted()).findFirst().orElse(null);
    }

    public List<Promotion> getListPromotion(){
        return promotion;
    }

    public void addVisitor(){
        countVisitors++;
    }
}
