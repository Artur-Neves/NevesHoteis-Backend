package br.com.nevesHoteis.domain;


import br.com.nevesHoteis.domain.Dto.HotelDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime availabilityDate;
    private BigDecimal dailyValue;
    @OneToOne()
    private Address address;
    public Hotel(HotelDto dto){
       this.name= dto.name();
       this.availabilityDate=dto.availabilityDate();
       this.dailyValue = dto.dailyValue();
       this.address= new Address(dto.address());
    }

    public Hotel merge(Hotel dto) {
        this.name= dto.getName();
        this.availabilityDate=dto.getAvailabilityDate();
        this.dailyValue = dto.getDailyValue();
        this.address= dto.getAddress();
        return this;
    }
}
