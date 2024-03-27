package org.c9504.consumers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.c9504.events.InventoryUpdated;
import org.c9504.events.OrderCreated;
import org.c9504.producers.OrderCreatedProducer;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class InventoryUpdatedConsumer {

    private static final Logger log = Logger.getLogger(InventoryUpdatedConsumer.class);

    @Inject
    OrderCreatedProducer orderCreatedProducer;

    @Incoming("inventory-updated-in")
    @Retry(delay = 10, maxRetries = 5)
    public CompletionStage<Void> inventoryUpdated(Message<InventoryUpdated> message) {
        if (message.getPayload().getMessage().equals("UPDATE")) {
            OrderCreated orderCreated = new OrderCreated();
            orderCreated.setOrderId(message.getPayload().getOrderId());
            orderCreated.setOrder(message.getPayload().getOrder());
            orderCreated.setMessage("UPDATED");
            log.infof("InventoryUpdatedConsumer#inventoryUpdated(): Received to update inventory with ID: %s", message.getPayload().getOrderId());
            orderCreatedProducer.publishEvent(orderCreated);
            return message.ack();
        }
        return message.nack(new Throwable("Inventory not updated"));
    }
}
