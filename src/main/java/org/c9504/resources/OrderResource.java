package org.c9504.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.c9504.entities.Order;
import org.c9504.events.OrderCreated;
import org.c9504.producers.OrderCreatedProducer;
import org.jboss.logging.Logger;

@Path("/orders")
public class OrderResource {

    private static final Logger log = Logger.getLogger(OrderResource.class);

    @Inject
    OrderCreatedProducer orderCreatedProducer;

    @GET
    @Path("/create/{orderId}/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Order createOrder(@PathParam("orderId") String orderId, @PathParam("customerId") String customerId) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setCustomerId(customerId);
        orderCreatedProducer.publishEvent(new OrderCreated(orderId, order, "CREATED"));
        log.infof("OrderResource#createOrder(): Order created with ID: %s", orderId);
        return order;
    }
}
