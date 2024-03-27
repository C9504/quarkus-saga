package org.c9504.consumers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.c9504.events.InventoryUpdated;
import org.c9504.events.OrderCreated;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;
import org.c9504.producers.InventoryUpdatedProducer;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class OrderCreatedConsumer {

    private static final Logger log = Logger.getLogger(OrderCreatedConsumer.class);

    @Inject
    InventoryUpdatedProducer inventoryUpdatedProducer;

    @Incoming("order-created-in")
    @Retry(delay = 10, maxRetries = 5)
    public CompletionStage<Void> consumeOrderCreated(Message<OrderCreated> order) {
        if (order.getPayload().getMessage().equals("CREATED")) {
            InventoryUpdated inventoryUpdated = new InventoryUpdated();
            inventoryUpdated.setOrderId(order.getPayload().getOrderId());
            inventoryUpdated.setOrder(order.getPayload().getOrder());
            inventoryUpdated.setMessage("UPDATE");
            log.infof("OrderCreatedConsumer#consumeOrderCreated(): Received order created with ID: %s", order.getPayload().getOrderId());
            inventoryUpdatedProducer.publishEvent(inventoryUpdated);
            return order.ack();
        } else if (order.getPayload().getMessage().equals("UPDATED")) {
            log.infof("OrderCreatedConsumer#consumeOrderCreated(): Received inventory updated with ID: %s", order.getPayload().getOrderId());
            return order.ack();
        }
        return order.nack(new Throwable("Order not created"));
    }
}
