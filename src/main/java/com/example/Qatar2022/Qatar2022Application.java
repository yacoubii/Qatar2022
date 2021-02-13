package com.example.Qatar2022;

import com.example.Qatar2022.entities.TicketEntity;
import com.example.Qatar2022.entities.UserEntity;
import com.example.Qatar2022.repositories.TicketRepository;
import com.example.Qatar2022.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.time.Month.JANUARY;
import static java.time.Month.JULY;

@SpringBootApplication
public class Qatar2022Application {

	public static void main(String[] args) {
		SpringApplication.run(Qatar2022Application.class, args);
	}
	@Configuration
	public class StudentConfig {
		@Bean
		CommandLineRunner commandLineRunner(UserRepository repository, TicketRepository ticketRepository){
			return args-> {
				UserEntity u = new UserEntity(
						"Asma",
						"JAZIRI",
						"Asma@gmail.com",
						"1234",
						"Tunisia",
						"23685462",

						LocalDate.of(1997, JULY,11)

				);

				repository.saveAll( List.of(u));


				TicketEntity t = new TicketEntity(
						"Bayern",
						"Real Madrid",
						LocalDate.of(1997, JULY,11),
						LocalTime.of(11,11,20),
						(float) 240,
						"Pelouse",
						"Final"



				);

				ticketRepository.saveAll( List.of(t));
			};
		}
	}

}
