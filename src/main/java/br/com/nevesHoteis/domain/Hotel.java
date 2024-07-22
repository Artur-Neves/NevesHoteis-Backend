package br.com.nevesHoteis.domain;


import br.com.nevesHoteis.controller.dto.hotel.HotelDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private LocalDate availabilityDate;
    private BigDecimal dailyValue;
    @Embedded
    private Address address;
    @OneToMany
    private List<Booking> bookings = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "hotel_photos", joinColumns = @JoinColumn(name = "hotel_id"))
    private List<byte[]> photos = new ArrayList<>();
    public Hotel(HotelDto dto){
       this.name= dto.getName();
       this.availabilityDate= dto.getAvailabilityDate() ;
       this.dailyValue = dto.getDailyValue();
       this.address= new Address(dto.getAddress());
       this.photos = convertListMultiPartFileInListByte(dto.getPhotos());
    }

    public Hotel merge(Hotel dto) {
        this.name= dto.getName();
        this.availabilityDate=dto.getAvailabilityDate();
        this.dailyValue = dto.getDailyValue();
        this.address= dto.getAddress();
        this.photos = dto.getPhotos();
        return this;
    }

    public Hotel(Long id, String name, LocalDate availabilityDate, BigDecimal dailyValue, Address address) {
        this.id = id;
        this.name = name;
        this.availabilityDate = availabilityDate;
        this.dailyValue = dailyValue;
        this.address = address;
    }
}
