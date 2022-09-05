package org.example.business.usecase;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.business.gateway.JuegoDomainEventRepository;
import org.example.domain.Juego;
import org.example.domain.command.CrearRondaCommand;
import org.example.domain.values.JuegoId;
import org.example.domain.values.JugadorId;
import org.example.domain.values.Ronda;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Optional;
import java.util.stream.Collectors;

public class CrearRondaUseCase extends UseCaseForCommand<CrearRondaCommand> {
    private final JuegoDomainEventRepository repository;
    public CrearRondaUseCase(JuegoDomainEventRepository repository) {
        this.repository = repository;
    }



    @Override
    public Flux<DomainEvent> apply(Mono<CrearRondaCommand> iniciarJuegoCommand) {



        return iniciarJuegoCommand.flatMapMany((command) -> repository
                .obtenerEventosPor(command.getJuegoId())
                .collectList()
                .flatMapIterable(events -> {
                    var juego = Juego.from(JuegoId.of(command.getJuegoId()), events);
                    var jugadores = command.getJugadores().stream()
                            .map(JugadorId::of)
                            .collect(Collectors.toSet());

                    Optional.ofNullable(juego.ronda())
                            .ifPresentOrElse(
                                    ronda -> juego.crearRonda(
                                            ronda.incrementarRonda(jugadores), command.getTiempo()
                                    ), () -> juego.crearRonda(
                                            new Ronda(jugadores,1 ), command.getTiempo())
                            );
                    return juego.getUncommittedChanges();
                }));
    }
}
