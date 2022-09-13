package org.example.cardgame.usecase;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.command.CrearRondaCommand;
import org.example.cardgame.domain.events.JuegoCreado;
import org.example.cardgame.domain.events.RondaCreada;
import org.example.cardgame.domain.events.TableroCreado;
import org.example.cardgame.domain.values.JugadorId;
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
class CrearRondaUseCaseTest {

    @Mock
    private JuegoDomainEventRepository repository;
    @InjectMocks
    private CrearRondaUseCase useCase;
    @Test
    void crearRonda(){
        var command = new CrearRondaCommand();
        command.setJuegoId("1234");
        command.setTiempo(60);
        command.setJugadores(Set.of("J1", "J2", "J3"));
        when(repository.obtenerEventosPor("1234"))
                .thenReturn(juegoCreado());
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
        event.setAggregateRootId("J1");
        var event2 = new TableroCreado(TableroId.of("TAB"), Set.of(JugadorId.of("1234"), JugadorId.of("JU02"), JugadorId.of("JU03")));
        event2.setAggregateRootId("TAB");
        return Flux.just(event, event2);
    }
}