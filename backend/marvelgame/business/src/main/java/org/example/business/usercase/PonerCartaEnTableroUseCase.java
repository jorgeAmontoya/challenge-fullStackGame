package org.example.business.usercase;

import co.com.sofka.domain.generic.Command;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.business.gateway.JuegoDomainEventRepository;
import org.example.domain.Juego;
import org.example.domain.command.PonerCartaEnTablero;
import org.example.domain.values.Carta;
import org.example.domain.values.JuegoId;
import org.example.domain.values.JugadorId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.logging.Logger;

public class PonerCartaEnTableroUseCase extends UseCaseForCommand<PonerCartaEnTablero>{
     private final Logger log = Logger.getLogger(PonerCartaEnTableroUseCase.class.getCanonicalName());

     private final JuegoDomainEventRepository repository;

    public PonerCartaEnTableroUseCase(JuegoDomainEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<PonerCartaEnTablero> ponerCartaEnTablero) {
        return ponerCartaEnTablero.flatMapMany((command)-> repository
                .obtenerEventosPor(command.getJuegoId())
                .collectList()
                .flatMapIterable(events->{
                    var juego = Juego.from(JuegoId.of(command.getJuegoId()), events);
                    var tableroId = juego.tablero().identity();
                    var jugadorId = JugadorId.of(command.getJugadorId());
                    var cartasDelJugador = juego.jugadores().get(jugadorId).mazo().value().cartas();
                    var cartaSeleccionado = seleccionarCarta(command.getCartaId(), cartasDelJugador);

                    var cantidad = (long) juego.tablero().partida()
                            .get(jugadorId).size();

                    if(cantidad >= 2){
                        throw new IllegalArgumentException("No puede poner mas de 2 cartas en el Tablero");
                    }
                    juego.ponerCartaEnTablero(tableroId, jugadorId, cartaSeleccionado);
                    return juego.getUncommittedChanges();

                }));
    }

    private Carta seleccionarCarta(String cartaId, Set<Carta> cartasDelJugador) {
        return cartasDelJugador
                .stream()
                .filter(c -> c.value().cartaId().value().equals(cartaId))
                .findFirst()
                .orElseThrow();

    }
}
