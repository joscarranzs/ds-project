package com.ds.core.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa una orden de entrega.
 */
public class Order {

    private final String id;
    private final Node origin;
    private final Node destination;

    private OrderStatus status;
    private int priority;
    private final LocalDateTime createdAt;
    private LocalDateTime deliveredAt;

    public Order(String id, Node origin, Node destination) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El id no puede estar vacío");
        }

        if (origin == null) {
            throw new IllegalArgumentException("El origen no puede ser nulo");
        }

        if (destination == null) {
            throw new IllegalArgumentException("El destino no puede ser nulo");
        }

        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.status = OrderStatus.PENDING;
        this.priority = 0;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public Node getOrigin() {
        return origin;
    }

    public Node getDestination() {
        return destination;
    }

    public OrderStatus getStatus() {
        return status;
    }
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        if (priority < 0) {
            throw new IllegalArgumentException("La prioridad no puede ser negativa");
        }
        this.priority = priority;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public boolean isDelivered() {
        return status == OrderStatus.DELIVERED;
    }

    public void markAsInProgress() {

        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException("La orden no puede pasar a IN_PROGRESS");
        }

        this.status = OrderStatus.IN_PROGRESS;
    }

    public void markAsDelivered() {

        if (status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("La orden ya fue entregada");
        }

        this.status = OrderStatus.DELIVERED;
        this.deliveredAt = LocalDateTime.now();
    }

    public void cancel() {

        if (status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("No se puede cancelar una orden entregada");
        }

        this.status = OrderStatus.CANCELLED;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", destination=" + destination +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", deliveredAt=" + deliveredAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof Order)) return false;
        Order order = (Order) o;

        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
