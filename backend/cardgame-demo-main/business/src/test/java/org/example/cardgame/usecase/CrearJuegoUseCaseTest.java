package org.example.cardgame.usecase;

import org.example.cardgame.domain.command.CrearJuegoCommand;
import org.example.cardgame.domain.events.JuegoCreado;
import org.example.cardgame.domain.events.JugadorAgregado;
import org.example.cardgame.gateway.ListaDeCartaService;
import org.example.cardgame.gateway.model.CartaMaestra;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CrearJuegoUseCaseTest {

    @Mock
    private ListaDeCartaService listaDeCartaService;

    @InjectMocks
    private CrearJuegoUseCase useCase;

    @Test
    void crearJuego(){
        var command = new CrearJuegoCommand();

        command.setJuegoId("1234");
        command.setJugadores(new HashMap<>());
        command.getJugadores().put("J1", "Juan");
        command.getJugadores().put("DDD", "Jhonatan");

        command.setJugadorPrincipalId("J1");

        when(listaDeCartaService.obtenerCartasDeMarvel()).thenReturn(history());

        StepVerifier.create(useCase.apply(Mono.just(command)))
                .expectNextMatches(
                        domainEvent -> {
                            var event = (JuegoCreado) domainEvent;
                            return  event.getJugadorPrincipal().value().equals("J1") && event.aggregateRootId().equals("1234");
                        }
                )
                .expectNextMatches(
                        domainEvent -> {
                            var event = (JugadorAgregado) domainEvent;
                            Assertions.assertEquals(5, event.getMazo().value().cantidad());
                            return "DDD".equals(event.getJuegoId().value()) && "Jhonatan".equals(event.getAlias());
                        }
                )
                .expectNextMatches(
                        domainEvent -> {
                            var event = (JugadorAgregado) domainEvent;
                            Assertions.assertEquals(5, event.getMazo().value().cantidad());
                            return "J1".equals(event.getJuegoId().value()) && "Juan".equals(event.getAlias());
                        }
                )
                .expectComplete()
                .verify();

    }

    private Flux<CartaMaestra> history() {

        return Flux.just(
                new CartaMaestra("C1", "a"),
                new CartaMaestra("C2", "b"),
                new CartaMaestra("C3", "c"),
                new CartaMaestra("C4", "d"),
                new CartaMaestra("C5", "e"),

                new CartaMaestra("C6", "f"),
                new CartaMaestra("C7", "g"),
                new CartaMaestra("C8", "h"),
                new CartaMaestra("C9", "i"),
                new CartaMaestra("C10", "j")

        );
    }

}