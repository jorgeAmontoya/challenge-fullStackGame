package org.example.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.values.JugadorId;
import org.example.domain.values.Mazo;

public class JugadorAdicionado extends DomainEvent {

    private  final JugadorId identity;
    private final String alias;
    private final Mazo mazo;
    public JugadorAdicionado(JugadorId identity, String alias, Mazo mazo) {
        super("marvelgame.jugadoradicionado");
        this.identity = identity;
        this.alias = alias;
        this.mazo = mazo;
    }

    public String getAlias() {
        return alias;
    }

    public JugadorId getIdentity() {
        return identity;
    }

    public Mazo getMazo() {
        return mazo;
    }
}
