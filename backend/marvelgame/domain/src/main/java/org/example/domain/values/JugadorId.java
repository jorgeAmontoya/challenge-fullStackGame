package org.example.domain.values;

import co.com.sofka.domain.generic.Identity;
import org.example.domain.Jugador;

public class JugadorId extends Identity {
    public JugadorId(String id) {
        super(id);
    }

    public static JugadorId of(String value) {
        return new JugadorId(value);
    }
}
