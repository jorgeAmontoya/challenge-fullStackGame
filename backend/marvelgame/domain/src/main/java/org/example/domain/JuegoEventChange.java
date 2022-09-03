package org.example.domain;

import co.com.sofka.domain.generic.EventChange;
import org.example.domain.events.*;

import java.util.HashMap;
import java.util.Objects;

public class JuegoEventChange extends EventChange {
    public JuegoEventChange(Juego juego) {

        apply((JuegoCreado event) -> {
            juego.jugadores = new HashMap<>();
            juego.jugadorPrincipal = event.getJugadorPrincipal();
        });

        apply((JugadorAgregado event) -> {
            juego.jugadores.put(event.getJuegoId(),
                    new Jugador(event.getJuegoId(), event.getAlias(), event.getMazo()));
        });

        apply((TableroCreado event)->{
            juego.tablero = new Tablero(event.getTableroId(), event.getJugadorIds());
        });

        apply((CartaPuestaEnTablero event)->{
            juego.tablero.adicionarPartida(event.getJugadorId(),event.getCarta());
        });

        apply((CartaQuitadaDelMazo event)->{
            juego.jugadores.get(event.getJugadorId()).quitarCartaDeMazo(event.getCarta());
        });

        apply((RondaCreada event) -> {
            if (Objects.isNull(juego.tablero)) {
                throw new IllegalArgumentException("Debe existir el tablero primero");
            }
            juego.ronda = event.getRonda();
            juego.tablero.ajustarTiempo(event.getTiempo());
        });

        apply((TableroCreado event) -> {
            juego.tablero = new Tablero(event.getTableroId(), event.getJugadorIds());
        });

    }


}
