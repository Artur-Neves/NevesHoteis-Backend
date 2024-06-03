package br.com.nevesHoteis.domain.Dto;

import br.com.nevesHoteis.domain.Address;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AddressUpdateDto (
        @NotNull
        Long id,
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
    AddressUpdateDto(Address address){
        this(address.getId(), address.getCep(), address.getState(),
                address.getCity(), address.getNeighborhood(), address.getPropertyLocation());
    }
}
