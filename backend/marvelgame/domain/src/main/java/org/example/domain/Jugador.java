package org.example.domain;

import co.com.sofka.domain.generic.Entity;
import org.example.domain.values.Carta;
import org.example.domain.values.JugadorId;
import org.example.domain.values.Mazo;

public class Jugador extends Entity<JugadorId> {

    private final String alias;
    private Mazo mazo;

    public Jugador(JugadorId id, String alias, Mazo mazo) {
        super(id);
        this.alias = alias;
        this.mazo = mazo;
        if (mazo.value().cantidad()<=0){
            throw new IllegalArgumentException("el mazo del jugador debe contener cinco cartas");
        }

    }

    public String alias() {
        return alias;
    }

    public Mazo mazo(){return mazo;}

    public void quitarCartaDeMazo(Carta carta) {
        mazo = mazo.retirarCarta(carta);
    }
}
