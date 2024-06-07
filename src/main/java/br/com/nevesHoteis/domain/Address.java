package br.com.nevesHoteis.domain;

import br.com.nevesHoteis.domain.Dto.AddressCompleteDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String propertyLocation;

    public Address(AddressCompleteDto dto) {
        this.cep=dto.cep();
        this.state= dto.state();
        this.city=dto.city();
        this.neighborhood=dto.neighborhood();
        this.propertyLocation= dto.propertyLocation();
    }
}
