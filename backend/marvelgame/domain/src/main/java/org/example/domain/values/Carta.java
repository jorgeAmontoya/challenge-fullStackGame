package org.example.domain.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Carta implements ValueObject<Carta.Props> {
    private final Integer poder;
    private final CartaMaestraId cartaId;
    private final Boolean estaHabilitada;

    private final Boolean estaOculta;

    public Carta(CartaMaestraId cartaId,Integer poder, Boolean estaOculta,Boolean estaHabilitada) {
        this.poder = poder;
        this.cartaId = cartaId;
        this.estaHabilitada = estaHabilitada;
        this.estaOculta = estaOculta;

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
            public Boolean estaOculta() {
                return estaOculta;
            }

            @Override
            public Boolean estaHabilitada() {
                return estaHabilitada;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carta carta = (Carta) o;
        return Objects.equals(cartaId, carta.cartaId) && Objects.equals(estaOculta, carta.estaOculta) && Objects.equals(estaHabilitada, carta.estaHabilitada) && Objects.equals(poder, carta.poder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartaId, estaOculta, estaHabilitada, poder);
    }





    public interface Props {

       Integer poder();
        CartaMaestraId cartaId();
       Boolean estaHabilitada();
        Boolean estaOculta();
    }
}
