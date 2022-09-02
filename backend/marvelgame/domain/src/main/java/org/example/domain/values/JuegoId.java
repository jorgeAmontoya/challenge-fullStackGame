package org.example.domain.values;

import co.com.sofka.domain.generic.Identity;

public class JuegoId extends Identity {
    public JuegoId(String juegoId) {
        super(juegoId);
    }

    public static JuegoId of(String juegoId) {
        return new JuegoId(juegoId);
    }
}
