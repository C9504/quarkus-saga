package org.c9504.deserializers;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import org.c9504.events.InventoryUpdated;

public class InventoryDeserializer extends ObjectMapperDeserializer<InventoryUpdated> {
    public InventoryDeserializer() {
        super(InventoryUpdated.class);
    }
}
