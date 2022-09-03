package org.example.domain.events;

import co.com.sofka.domain.generic.Command;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.values.JugadorId;
import org.example.domain.values.TableroId;

import java.util.Set;

public class TableroCreado extends DomainEvent {
    private final TableroId tableroId;

    private final Set<JugadorId> jugadoresIds;

    public TableroCreado(TableroId tableroId, Set<JugadorId> jugadorIds) {
        super("domain.TableroCreado");
        this.tableroId = tableroId;
        this.jugadoresIds = jugadorIds;
    }

    public Set<JugadorId> getJugadorIds() {
        return jugadoresIds;
    }

    public TableroId getTableroId() {
        return tableroId;
    }
}
