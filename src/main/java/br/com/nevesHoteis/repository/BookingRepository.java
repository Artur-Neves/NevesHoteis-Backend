package br.com.nevesHoteis.repository;

import br.com.nevesHoteis.domain.Booking;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT CASE WHEN COUNT(b)>0 THEN TRUE ELSE FALSE END FROM Booking b WHERE " +
            "((b.startDate BETWEEN :date1 and :date2) OR (b.endDate BETWEEN :date1 and :date2)) and b.id<>:id ")
    boolean existsDateBetweenStartDateOrEndDate(@Param("date1") LocalDate date, @Param("date2") LocalDate date2, @Param("id") Long id);
}
