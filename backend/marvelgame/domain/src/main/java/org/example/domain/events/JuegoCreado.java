package org.example.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.values.Estado;
import org.example.domain.values.JugadorId;

public class JuegoCreado extends DomainEvent {
    private final JugadorId jugadorPrincipal;
    public JuegoCreado(JugadorId jugadorPrincipal) {
        super("domain.JuegoCreado");
        this.jugadorPrincipal = jugadorPrincipal;

    }

    public JugadorId getJugadorPrincipal() {
        return jugadorPrincipal;
    }
}
