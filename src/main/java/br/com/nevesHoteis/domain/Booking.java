package br.com.nevesHoteis.domain;

import br.com.nevesHoteis.controller.dto.BookingDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate cancellationDeadline;
    @ManyToOne
    private SimpleUser simpleUser;
    @ManyToOne
    private Hotel hotel;

    public Booking(Long id, LocalDate startDate, LocalDate endDate, SimpleUser simpleUser, Hotel hotel) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.simpleUser = simpleUser;
        this.hotel = hotel;
        this.cancellationDeadline = startDate.minusDays(2);
    }

    public Booking(SimpleUser simpleUser, Hotel hotel, LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.simpleUser = simpleUser;
        this.hotel = hotel;
        this.cancellationDeadline = startDate.minusDays(2);
    }

    public void merge(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.cancellationDeadline = startDate.minusDays(2);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", cancellationDeadline=" + cancellationDeadline +
                ", simpleUser=" + simpleUser +
                ", hotel=" + hotel +
                '}';
    }
}
