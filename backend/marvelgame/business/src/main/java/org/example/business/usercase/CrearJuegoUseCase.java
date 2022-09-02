package org.example.business.usercase;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.business.gateway.ConsultaCartasMaestrasService;
import org.example.business.gateway.model.cartaMaestra;
import org.example.domain.CrearJuegoCommand;
import org.example.domain.Juego;
import org.example.domain.JugadoresFactory;
import org.example.domain.values.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CrearJuegoUseCase implements Function<Mono<CrearJuegoCommand>, Flux<DomainEvent>> {

    private final ConsultaCartasMaestrasService service;

    public CrearJuegoUseCase(ConsultaCartasMaestrasService service) {
        this.service = service;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<CrearJuegoCommand> crearJuegoCommandMono) {
        return crearJuegoCommandMono.flatMapMany((command)->{

            var Factory = new JugadoresFactory();
            service.consultarTodasLasCartas();


            command.
                    getJugadores().
                    forEach((id, alias) ->
                    Factory.agregarJugador(JugadorId.of(id), alias, generarMazo())
            );
            var juego = new Juego(JuegoId.of(command.getJuegoId()),Factory );

            return Flux.fromIterable(juego.getUncommittedChanges());
        });
    }

    private Mazo generarMazo(List<cartaMaestra> cartas) {
        Collections.shuffle(cartas);
        var mazoDelJugador = cartas.stream().limit(5)
                .map(carta -> new Carta(carta.getPoder(), CartaMaestraId.of(carta.getId()), false))
                .collect(Collectors.toList());

        cartas.removeIf(cartaMaestra -> mazoDelJugador.stream().anyMatch(carta ->{
            var id = carta.value().cartaId().value();
            return cartaMaestra.getId().equals(id);

        }));
        return new Mazo(new HashSet<>(mazoDelJugador));

    }
}
