package org.example.cardgame.usecase;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.command.CrearRondaCommand;
import org.example.cardgame.events.JuegoCreado;
import org.example.cardgame.events.RondaCreada;
import org.example.cardgame.events.TableroCreado;
import org.example.cardgame.gateway.JuegoDomainEventRepository;
import org.example.cardgame.values.JugadorId;
import org.example.cardgame.values.TableroId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class CrearRondaUseCaseTest {
    @InjectMocks
    private CrearRondaUseCase useCase;

    @Mock
    private JuegoDomainEventRepository repository;

    @Test
    void crearRonda(){
        //ARRANGE
        var command = new CrearRondaCommand();
        command.setJuegoId("1234");
        command.setTiempo(60);
        command.setJugadores(Set.of("J1", "J2", "J3"));

        when(repository.obtenerEventosPor("1234"))
                .thenReturn(juegoCreado());

        //ASSERT & ACT
        StepVerifier
                .create(useCase.apply(Mono.just(command)))
                .expectNextMatches(domainEvent -> {
                    var event = (RondaCreada) domainEvent;
                    return event.aggregateRootId().equals("1234")
                            && event.getTiempo().equals(60)
                            && event.getRonda().value().jugadores()
                            .equals(Set.of(JugadorId.of("J1"), JugadorId.of("J2"), JugadorId.of("J3")));
                })
                .expectComplete()
                .verify();
    }

    private Flux<DomainEvent> juegoCreado() {
        var event = new JuegoCreado(JugadorId.of("J1"));
        event.setAggregateRootId("1234");

        var event2 = new TableroCreado(TableroId.of("TAB"), Set.of(JugadorId.of("J1"), JugadorId.of("J2"), JugadorId.of("J3")));
        event2.setAggregateRootId("1234");

        return Flux.just(event, event2);
    }

}