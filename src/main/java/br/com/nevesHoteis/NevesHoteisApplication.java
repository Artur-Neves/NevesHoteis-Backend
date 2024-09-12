package br.com.nevesHoteis;

import br.com.nevesHoteis.domain.Hotel;
import br.com.nevesHoteis.domain.Promotion;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class NevesHoteisApplication {

	public static void main(String[] args) throws IOException {
		Hotel hotel = new Hotel(1L, "mexerica", BigDecimal.valueOf(150L), null);
		Promotion promotion = new Promotion(1L, BigDecimal.valueOf(50L), LocalDateTime.now(), LocalDateTime.now(), hotel);
		SpringApplication.run(NevesHoteisApplication.class, args);
	}

}
