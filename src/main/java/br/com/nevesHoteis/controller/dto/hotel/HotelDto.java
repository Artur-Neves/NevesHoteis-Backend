package br.com.nevesHoteis.controller.dto.hotel;

import br.com.nevesHoteis.controller.dto.address.AddressDto;
import br.com.nevesHoteis.domain.Hotel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotelDto{
        @NotBlank
        @Size( min = 2, max = 30)
        private String name;
        @NotNull
        @Positive
        @Max(999999)
        private BigDecimal dailyValue;
        @NotNull
        @Size(max = 10, min = 4)
        private List<MultipartFile> photos;
        @NotNull
        @Valid
        private AddressDto address;
        static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public HotelDto(Hotel hotel) {
        this(hotel.getName()
                , hotel.getDailyValue(), null,new AddressDto( hotel.getAddress()));
    }
}