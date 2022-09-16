package org.example.cardgame.domain.command;

import co.com.sofka.domain.generic.Command;

import java.util.Set;


public class FinalizarRondaCommand extends Command {
    private String juegoId;

    private Set<String>  jugadoresSelecionados;

    private String jugadorPotenciado;

    public String getJugadorpotenciado() {
        return jugadorPotenciado;
    }

    public void setJugadorpotenciado(String jugadorpotenciado) {
        this.jugadorPotenciado = jugadorpotenciado;
    }

    public String getJuegoId() {
        return juegoId;
    }


    public void setJuegoId(String juegoId) {
        this.juegoId = juegoId;
    }

    public Set<String> getJugadoresSelecionados() {
        return jugadoresSelecionados;
    }

    public void setJugadoresSelecionados(Set<String> jugadoresSelecionados) {
        this.jugadoresSelecionados = jugadoresSelecionados;
    }
}
