package org.example.cardgame.application;

import org.example.business.usercase.IniciarJuegoUseCase;
import org.example.domain.values.Carta;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class AppService {

    public static void main(String[] args) {
        SpringApplication.run(AppService.class, args);


    }

}
