package org.example.cardgame.usecase;


import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.command.IniciarRondaCommand;
import org.example.cardgame.domain.events.JuegoCreado;
import org.example.cardgame.domain.events.RondaCreada;
import org.example.cardgame.domain.events.RondaIniciada;
import org.example.cardgame.domain.events.TableroCreado;
import org.example.cardgame.domain.values.JugadorId;
import org.example.cardgame.domain.values.Ronda;
import org.example.cardgame.domain.values.TableroId;
import org.example.cardgame.gateway.JuegoDomainEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IniciarRondaUseCaseTest {

    @Mock
    private JuegoDomainEventRepository repository;

    @InjectMocks
    private IniciarRondaUseCase useCase;

    @Test
    void iniciarRonda(){
        var command = new IniciarRondaCommand();
        command.setJuegoId("PPPP");

        when(repository.obtenerEventosPor("PPPP")).thenReturn(history());

        StepVerifier.create(useCase.apply(Mono.just(command)))
                .expectNextMatches(domainEvent -> {
                    var event = (RondaIniciada) domainEvent;
                    return event.aggregateRootId().equals("PPPP");
                })
                .expectComplete()
                .verify();
    }

    private Flux<DomainEvent> history() {
        var event = new JuegoCreado(JugadorId.of("PPPP"));
        event.setAggregateRootId("XXXX");

        var event2 = new TableroCreado(TableroId.of("TAB1"), Set.of(JugadorId.of("Jorge"), JugadorId.of("Heyler"), JugadorId.of("Luz")));
        event2.setAggregateRootId("XXXX");

        var event3 = new RondaCreada(new Ronda(1, event2.getJugadorIds()), 30);
        event3.setAggregateRootId("XXXX");

        return Flux.just(event, event2, event3);
    }
}