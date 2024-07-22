package br.com.nevesHoteis.controller.dto.address;

import br.com.nevesHoteis.domain.Address;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto{
        @Pattern(regexp = "\\d{5}-\\d{3}")
        @NotBlank
        private String cep;
        @NotBlank
        @Pattern(regexp = "[A-Z]{2}")
        private String state;
        @NotBlank
        @Size( min = 2, max = 30)
        private String city;
        @NotBlank
        @Size( min = 2, max = 50)
        private String neighborhood;
        @NotBlank
        @Size( min = 2, max = 50)
        private  String propertyLocation;

    public AddressDto(Address address) {
        this.cep = address.getCep();
        this.state = address.getState();
        this.city = address.getCity();
        this.neighborhood = address.getNeighborhood();
        this.propertyLocation = address.getPropertyLocation();
    }
}
