package org.c9504.deserializers;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import org.c9504.events.OrderCreated;

public class OrderDeserializer extends ObjectMapperDeserializer<OrderCreated> {
    public OrderDeserializer() {
        super(OrderCreated.class);
    }
}
