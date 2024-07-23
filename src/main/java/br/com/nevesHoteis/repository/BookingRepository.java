package br.com.nevesHoteis.repository;

import br.com.nevesHoteis.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
