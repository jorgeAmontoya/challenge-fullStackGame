package org.example.domain;

import org.example.domain.values.JugadorId;
import org.example.domain.values.Mazo;

import java.util.HashSet;
import java.util.Set;

public class JugadorFactory {

    private final Set<Jugador> jugadores;

    public JugadorFactory() {
        this.jugadores = new HashSet<>();
    }

    public void agregarJugador(JugadorId jugadorId, String alias, Mazo mazo ){

        jugadores.add(new Jugador(jugadorId, alias, mazo));

    }

    protected  Set<Jugador> getJugadores() {
        return jugadores;
    }
}
