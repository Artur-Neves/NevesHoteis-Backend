package br.com.nevesHoteis.controller.dto.address;

import br.com.nevesHoteis.domain.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressCompleteDto (
     @Pattern(regexp = "\\d{5}-\\d{3}")
     @NotBlank
     String cep,
     @NotBlank
     @Pattern(regexp = "[A-Z]{2}")
     String state,
     @NotBlank
     @Size( min = 2, max = 30)
     String city,
     @NotBlank
     @Size( min = 2, max = 50)
     String neighborhood,
     @NotBlank
     @Size( min = 2, max = 50)
     String propertyLocation){

    public AddressCompleteDto(Address address){
            this( address.getCep(), address.getState(),
                     address.getCity(), address.getNeighborhood(), address.getPropertyLocation());
    }
}


