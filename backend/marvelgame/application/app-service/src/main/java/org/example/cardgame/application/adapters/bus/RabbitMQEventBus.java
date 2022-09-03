package org.example.cardgame.application.adapters.bus;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.application.ApplicationConfig;
import org.example.cardgame.application.GsonEventSerializer;
import org.example.cardgame.application.generic.EventBus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitMQEventBus implements EventBus {
    private final RabbitTemplate rabbitTemplate;
    private final GsonEventSerializer serializer;

    public RabbitMQEventBus(RabbitTemplate rabbitTemplate,  GsonEventSerializer serializer) {
        this.serializer = serializer;
        this.rabbitTemplate = rabbitTemplate;
    }
    @Override
    public void publish(DomainEvent event) {
        var notification = new Notification(
                event.getClass().getTypeName(),
                serializer.serialize(event)
        );


        rabbitTemplate.convertAndSend(
                ApplicationConfig.EXCHANGE, event.type, notification.serialize().getBytes()
        );
    }

    @Override
    public void publishError(Throwable errorEvent) {

    }
}
