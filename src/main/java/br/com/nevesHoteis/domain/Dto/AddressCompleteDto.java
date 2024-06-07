package br.com.nevesHoteis.domain.Dto;

import br.com.nevesHoteis.domain.Address;
import org.apache.catalina.filters.AddDefaultCharsetFilter;

public record AddressCompleteDto (
     Long id,
     String cep,
     String state,
     String city,
     String neighborhood,
     String propertyLocation){
    public AddressCompleteDto(Address address){
        this(address.getId(), address.getCep(), address.getState(),
                 address.getCity(), address.getNeighborhood(), address.getPropertyLocation());
    }
}


