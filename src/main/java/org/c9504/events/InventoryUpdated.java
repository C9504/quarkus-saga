package org.c9504.events;

import org.c9504.entities.Order;

public class InventoryUpdated {

    private String orderId;
    private Order order;
    private String message = "UPDATED";

    public InventoryUpdated() {

    }

    public InventoryUpdated(String orderId, Order order, int quantity, String message) {
        this.orderId = orderId;
        this.order = order;
        this.message = message;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
