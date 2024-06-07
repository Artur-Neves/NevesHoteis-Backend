package br.com.nevesHoteis.domain.Dto;

import br.com.nevesHoteis.domain.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressDto(
        @Pattern(regexp = "\\d{5}-\\d{3}")
        @NotBlank
        String cep,
        @NotBlank
        String state,
        @NotBlank
        String city,
        @NotBlank
        String neighborhood,
        @NotBlank
        String propertyLocation){
    public AddressDto(Address address){
        this(address.getCep(), address.getState(),
                address.getCity(), address.getNeighborhood(), address.getPropertyLocation());
    }
}
