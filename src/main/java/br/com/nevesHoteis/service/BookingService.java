package br.com.nevesHoteis.service;

import br.com.nevesHoteis.controller.dto.BookingDto;
import br.com.nevesHoteis.controller.dto.BookingUpdateDto;
import br.com.nevesHoteis.domain.Booking;
import br.com.nevesHoteis.domain.Hotel;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.repository.BookingRepository;
import br.com.nevesHoteis.service.validation.booking.ValidateBooking;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository repository;
    @Autowired
    private SimpleUserService simpleUserService;
    @Autowired
    private HotelService hotelService;
    @Setter
    @Autowired
    private List<ValidateBooking> validateBooking;


    public Booking save(BookingDto bookingDto) {
        Booking booking = convertBookingDtoInBooking(bookingDto);
        validateBooking.forEach(vb -> vb.validateBooking(booking));
        return repository.save(booking);
    }

    public Page<Booking> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Booking updateDates(BookingUpdateDto bookingDto, Long id){
        Booking booking = findById(id);
        booking.merge(bookingDto.startDate(), bookingDto.endDate());
        validateBooking.forEach(vb -> vb.validateBooking(booking));
        return repository.save(booking);
    }

    public void delete(Long id){
        repository.delete(findById(id));
    }

    public Booking findById(Long id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    private Booking convertBookingDtoInBooking(BookingDto dto){
        Hotel hotel = hotelService.findById(dto.idHotel());
        return new Booking(getSimpleUserByAuthentication(), hotel, dto.startDate(), dto.endDate());
    }

    protected SimpleUser getSimpleUserByAuthentication(){
        String login = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return simpleUserService.findByUserLogin(login);
    }


}
