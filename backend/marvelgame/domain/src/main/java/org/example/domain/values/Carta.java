package org.example.domain.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Carta implements ValueObject<Carta.Props> {
    private final Integer poder;
    private final CartaMaestraId cartaId;
    private final Boolean estaHabilitada;

    public Carta(Integer poder,  CartaMaestraId cartaId, Boolean estaHabilitada) {
        this.poder = Objects.requireNonNull(poder);
        this.cartaId = Objects.requireNonNull(cartaId);
        this.estaHabilitada = Objects.requireNonNull(estaHabilitada);

        if (this.poder <= 0){
            throw new IllegalArgumentException("El poder de la carta no puede ser menor o igual a cero");
        }
    }

    @Override
    public Carta.Props value() {
        return new Props() {
            @Override
            public Integer poder() {
                return poder;
            }

            @Override
            public CartaMaestraId cartaId() {
                return cartaId;
            }

            @Override
            public Boolean estaHabilitada() {
                return estaHabilitada;
            }
        };
    }

    public interface Props {

       Integer poder();
        CartaMaestraId cartaId();
       Boolean estaHabilitada();
    }
}
