package org.example.domain;

import org.example.domain.values.JugadorId;
import org.example.domain.values.Mazo;

import java.util.HashSet;
import java.util.Set;

public class JugadoresFactory {

    private final Set<Jugador> jugadores;

    public JugadoresFactory() {
        this.jugadores = new HashSet<>();
    }

    public void agregarJugador(JugadorId Id, String alias, Mazo mazo ){

        this.jugadores.add(new Jugador(Id, alias, mazo));

    }

    protected Set<Jugador> jugadores() {
        return jugadores;
    }
}
