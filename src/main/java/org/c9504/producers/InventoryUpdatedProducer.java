package org.c9504.producers;

import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.c9504.events.InventoryUpdated;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.Random;

@ApplicationScoped
public class InventoryUpdatedProducer {

    private final Random random = new Random();

    @Inject
    @Channel("inventory-updated-out")
    Emitter<Record<Integer, InventoryUpdated>> emitter;


    public void publishEvent(InventoryUpdated order) {
        emitter.send(Record.of(random.nextInt(), order));
    }

}
