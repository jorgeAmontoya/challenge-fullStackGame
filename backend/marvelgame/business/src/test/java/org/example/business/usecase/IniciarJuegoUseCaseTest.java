package org.example.business.usecase;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.business.gateway.JuegoDomainEventRepository;
import org.example.domain.command.IniciarJuegoCommand;
import org.example.domain.events.JuegoCreado;
import org.example.domain.events.TableroCreado;
import org.example.domain.values.JugadorId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class IniciarJuegoUseCaseTest {

    @InjectMocks
    private IniciarJuegoUseCase useCase;

    @Mock
    private JuegoDomainEventRepository repository;

    @Test
    void iniciarJuego(){
        //ASSERT
        var command = new IniciarJuegoCommand();
        command.setJuegoId("1234");

        when(repository.obtenerEventosPor("1234"))
                .thenReturn(juegoCreado());

        //ACT & ASSERT
        StepVerifier
                .create(useCase.apply(Mono.just(command)))
                .expectNextMatches(domainEvent -> {
                    var event = (TableroCreado) domainEvent;
                    return event.aggregateRootId().equals("1234");
                })
                .expectComplete()
                .verify();
    }

    private Flux<DomainEvent> juegoCreado() {
        var event = new JuegoCreado(JugadorId.of("jug"));
        event.setAggregateRootId("1234");
        return Flux.just(event);
    }

}
