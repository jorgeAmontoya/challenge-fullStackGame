package org.example.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.values.JugadorId;
import org.example.domain.values.Mazo;

public class JugadorAgregado extends DomainEvent {

    private  final JugadorId identity;
    private final String alias;
    private final Mazo mazo;
    public JugadorAgregado(JugadorId identity, String alias, Mazo mazo) {
        super("domain.jugadoradicionado");
        this.identity = identity;
        this.alias = alias;
        this.mazo = mazo;
    }

    public JugadorId getJugadorId() {
        return identity;
    }
    public String getAlias() {
        return alias;
    }

    public JugadorId getJuegoId() {
        return identity;
    }

    public Mazo getMazo() {
        return mazo;
    }
}
