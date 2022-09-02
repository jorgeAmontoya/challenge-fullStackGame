package org.example.business.gateway;

import org.example.business.gateway.model.cartaMaestra;
import reactor.core.publisher.Flux;

public interface ConsultaCartasMaestrasService {

    Flux<cartaMaestra> consultarTodasLasCartas();
}
