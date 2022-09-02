package org.example.domain.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Set;

public class Mazo implements ValueObject<Mazo.Props> {

    private final Set<Carta> cartas;
   private final Integer cantidad;

    public Mazo(Set<Carta> cartas) {
        this.cartas = cartas;
        this.cantidad = cartas.size();
    }

    @Override
    public Props value() {
        return new Props(){

            @Override
            public Set<Carta> cartas() {
                return cartas;
            }

            @Override
            public Integer cantidad() {
                return cantidad;
            }
        };
    }

    public interface Props {
        Set<Carta> cartas();
        Integer cantidad();

    }
}
