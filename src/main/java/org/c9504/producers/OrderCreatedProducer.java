package org.c9504.producers;

import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.c9504.events.OrderCreated;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.Random;

@ApplicationScoped
public class OrderCreatedProducer {

    private final Random random = new Random();

    @Inject
    @Channel("order-created-out")
    Emitter<Record<Integer, OrderCreated>> emitter;

    public void publishEvent(OrderCreated order) {
        emitter.send(Record.of(random.nextInt(), order));
    }

}
