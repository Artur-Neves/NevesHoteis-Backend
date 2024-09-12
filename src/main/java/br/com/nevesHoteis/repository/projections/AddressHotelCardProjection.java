package br.com.nevesHoteis.repository.projections;

import br.com.nevesHoteis.domain.Address;

import java.math.BigDecimal;

public record AddressHotelCardProjection(
        String city,
        String state
) {
    public AddressHotelCardProjection(Address address) {
       this(address.getCity(), address.getState());
    }

    public AddressHotelCardProjection(String city, String state) {
        this.city = city;
        this.state = state;
    }
}
