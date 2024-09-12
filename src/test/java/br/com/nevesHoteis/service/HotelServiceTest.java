package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.Address;
import br.com.nevesHoteis.domain.Hotel;
import br.com.nevesHoteis.repository.HotelRepository;
import br.com.nevesHoteis.repository.projections.HotelDatesCardProjection;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static br.com.nevesHoteis.Fixture.buildHotel;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest{
    @InjectMocks
    private HotelService service;
    @Mock
    private HotelRepository repository;
    @Mock
    private Hotel tMock;
    @Mock
    private Pageable pageable;
    @Test
    @DisplayName("Testando a persistência de uma entidade")
    void test01(){
        given(repository.save(any(Hotel.class))).willReturn(tMock);
        assertEquals(tMock, service.save(tMock));
        then(repository).should().save(tMock);
    }
    @Test
    @DisplayName("Testando o retorno de todas as entidades")
    void test02(){
        Page<HotelDatesCardProjection> pageHotel = new PageImpl<Hotel>(List.of(buildHotel())).map(HotelDatesCardProjection::new);
        given(repository.findAllHotelForCard(pageable)).willReturn(pageHotel);
        assertEquals(pageHotel, service.findAll(pageable));
        then(repository).should().findAllHotelForCard(pageable);
    }
    @Test
    @DisplayName("Testando a exlusão de uma entidade")
    void test03(){
        given(repository.findById(anyLong())).willReturn(Optional.of(tMock));
        service.delete(anyLong());
        then(repository).should().delete(any());
    }
    @Test
    @DisplayName("Testando a procura por de uma entidade pelo Id")
    void test04(){
        given(repository.findById(anyLong())).willReturn(Optional.of(tMock));
        assertEquals( tMock ,service.findById(anyLong()));
        then(repository).should().findById(anyLong());
    }
    @Test
    @DisplayName("Testando o lançamento de uma exception ao não achar uma entidade com o id passado")
    void test05(){
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()->service.findById(anyLong()));
        then(repository).should().findById(anyLong());
    }
    @Test
    @DisplayName("Testando o lançamento de uma exception ao não achar uma entidade com o id passado")
    void test06(){
        Hotel hotel = randomHotel();
        given(repository.findById(anyLong())).willReturn(Optional.of(tMock));
        given(tMock.merge(hotel)).willReturn(hotel);
        assertEquals(hotel , service.update( anyLong(), hotel));
        then(repository).should().findById(anyLong());
        then(tMock).should().merge(hotel);
    }
    private Hotel randomHotel(){
        return new Hotel(1L, "Hotel fiveStars",  new BigDecimal(35), randomAddress());
    }
    private Address randomAddress(){
        return new Address( "45502-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
    }
}