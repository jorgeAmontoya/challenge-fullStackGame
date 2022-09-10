package org.example.cardgame.usecase.usecase;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.command.FinalizarRondaCommand;
import org.example.cardgame.domain.events.*;
import org.example.cardgame.domain.values.*;
import org.example.cardgame.usecase.gateway.JuegoDomainEventRepository;
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
class FinalizarRondaUseCaseTest {
    @InjectMocks
    private FinalizarRondaUseCase useCase;

    @Mock
    private JuegoDomainEventRepository repository;

    @Test
    void finalizarRonda() {
        //ARRANGE
        var command = new FinalizarRondaCommand();
        command.setJuegoId("123");

        //ACT
        when(repository.obtenerEventosPor("123"))
                .thenReturn(history());

        //ASSERT
        StepVerifier
                .create(useCase.apply(Mono.just(command)))
                .expectNextMatches(domainEvent -> {
                    var event = (CartasAsignadasAJugador) domainEvent;

                    return event.aggregateRootId().equals("123")
                            && event.getGanadorId().equals(JugadorId.of("J1"))
                            && event.getPuntos().equals(950)
                            && event.getCartasApuesta().equals(
                                    Set.of(
                                            new Carta(CartaMaestraId.of("CHulk"),
                                                    950,
                                                    true,
                                                    true,
                                                    "www"),
                            new Carta(CartaMaestraId.of("CThanos"), 800, true, true, "wwwwo")));
                }).expectNextMatches(domainEvent -> {
                    var event = (RondaTerminada) domainEvent;
                    return event.aggregateRootId().equals("123")
                            && event.getTableroId().equals(TableroId.of("T1"))
                            && event.getJugadorIds().equals(Set.of(JugadorId.of("J1"), JugadorId.of("J2")));
                })
                .expectComplete()
                .verify();

    }

    private Flux<DomainEvent> history() {
        var event = new JuegoCreado(JugadorId.of("J1"));
        event.setAggregateRootId("123");

        var event2 = new JugadorAgregado(
                JugadorId.of("J1"), "Anderson",
                new Mazo(Set.of(
                        new Carta(CartaMaestraId.of("01"), 950, true, true,"wwwA"),
                        new Carta(CartaMaestraId.of("10"), 102, true, true, "wwwB"),
                        new Carta(CartaMaestraId.of("05"), 101, true, true, "wwwC"),
                        new Carta(CartaMaestraId.of("07"), 104, true, true, "wwwD"),
                        new Carta(CartaMaestraId.of("04"), 150, true, true, "wwwE"),
                        new Carta(CartaMaestraId.of("06"), 160, true, true, "wwwF")
                )));
        event2.setAggregateRootId("123");

        var event3 = new JugadorAgregado(
                JugadorId.of("J2"), "steven",
                new Mazo(Set.of(
                        new Carta(CartaMaestraId.of("31"), 800, true, true, "wwwG"),
                        new Carta(CartaMaestraId.of("34"), 102, true, true, "wwwH"),
                        new Carta(CartaMaestraId.of("35"), 101, true, true, "wwwI"),
                        new Carta(CartaMaestraId.of("40"), 104, true, true, "wwwJ"),
                        new Carta(CartaMaestraId.of("45"), 150, true, true, "wwwK"),
                        new Carta(CartaMaestraId.of("49"), 160, true, true, "wwwL")
                )));
        event3.setAggregateRootId("123");

        var event4 = new TableroCreado(TableroId.of("T1"),
                Set.of(
                        JugadorId.of("J1"),
                        JugadorId.of("J2")
                )
        );
        event4.setAggregateRootId("123");

        var event5 = new RondaCreada(
                new Ronda(1,
                        Set.of(JugadorId.of("J1"),
                                JugadorId.of("J2")
                        )
                ), 60);
        event5.setAggregateRootId("123");

        var event6 = new RondaIniciada();
        event6.setAggregateRootId("123");


        //JUGADOR 1
        var event7 = new CartaPuestaEnTablero(
                event4.getTableroId(),
                event2.getJugadorId(),
                new Carta(
                        CartaMaestraId.of("CHulk"),
                        950,
                        true,
                        true,
                        "wwwA"));
        event7.setAggregateRootId("123");

        var event8 = new CartaQuitadaDelMazo(
                event2.getJugadorId(),
                new Carta(
                        CartaMaestraId.of("CHulk"),
                        950,
                        true,
                        true,
                        "wwwA"));
        event8.setAggregateRootId("123");


        //JUGADOR 2
        var event9 = new CartaPuestaEnTablero(
                event4.getTableroId(),
                event3.getJugadorId(),
                new Carta(
                        CartaMaestraId.of("CThanos"),
                        800,
                        true,
                        true,
                        "wwwB"));
        event9.setAggregateRootId("123");

        var event10 = new CartaQuitadaDelMazo(
                event3.getJugadorId(),
                new Carta(
                        CartaMaestraId.of("CThanos"),
                        800,
                        true,
                        true,
                        "wwwB"));
        event10.setAggregateRootId("123");

        return Flux.just(event, event2, event3, event4, event5, event6, event7, event8, event9, event10);
    }

}