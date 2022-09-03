package org.example.cardgame.application.adapters.service;

import org.example.business.gateway.ListaDeCartaService;
import org.example.business.gateway.model.CartaMaestra;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

public class DataConsultarCartaMaestraService implements ListaDeCartaService {

    private final ReactiveMongoTemplate template;

    public DataConsultarCartaMaestraService(ReactiveMongoTemplate template) {
        this.template = template;
    }
    @Override
    public Flux<CartaMaestra> obtenerCartasDeMarvel() {
        return template.findAll(CartaMaestra.class, "cards");
    }
}
