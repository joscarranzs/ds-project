package com.ds.core.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa un pedido dentro del sistema de gestión.
 * <p>
 * Cada pedido está asociado a un {@link Client}, tiene un origen y destino
 * representados como {@link Node}, y atraviesa un ciclo de vida definido por
 * los estados de {@link OrderStatus}. La prioridad y el tiempo estimado
 * permiten al sistema optimizar la asignación y el orden de atención.
 * </p>
 *
 * <p>
 * <b>Ciclo de vida:</b>
 * </p>
 *
 * <pre>
 * PENDING → PREPARING → IN_TRANSIT → DELIVERED
 *    ↓          ↓           ↓
 *    └──────────┴───────────┘ → CANCELLED
 * </pre>
 */
public class Order {

  private final String id;
  private final Client client;
  private final Node origin;
  private final Node destination;
  private OrderStatus status;
  private int priority;
  private double estimatedTime;
  private final LocalDateTime createdAt;
  private LocalDateTime deliveredAt;

  /**
   * Construye un nuevo pedido con estado inicial {@link OrderStatus#PENDING}.
   *
   * @param id          identificador único del pedido
   * @param client      cliente que realiza el pedido
   * @param origin      nodo de origen (restaurante / punto de partida)
   * @param destination nodo de destino (ubicación del cliente)
   * @throws IllegalArgumentException si algún argumento es {@code null} o
   *                                  {@code id} está en blanco
   */
  public Order(String id, Client client, Node origin, Node destination) {
    if (id == null || id.isBlank())
      throw new IllegalArgumentException("id cannot be blank");
    if (client == null)
      throw new IllegalArgumentException("client cannot be null");
    if (origin == null)
      throw new IllegalArgumentException("origin cannot be null");
    if (destination == null)
      throw new IllegalArgumentException("destination cannot be null");
    this.id = id;
    this.client = client;
    this.origin = origin;
    this.destination = destination;
    this.status = OrderStatus.PENDING;
    this.priority = 0;
    this.estimatedTime = 0.0;
    this.createdAt = LocalDateTime.now();
  }

  /**
   * @return identificador único del pedido
   */
  public String getId() {
    return id;
  }

  /**
   * @return cliente asociado al pedido
   */
  public Client getClient() {
    return client;
  }

  /**
   * @return nodo de origen del pedido
   */
  public Node getOrigin() {
    return origin;
  }

  /**
   * @return nodo de destino del pedido
   */
  public Node getDestination() {
    return destination;
  }

  /**
   * @return estado actual del pedido
   */
  public OrderStatus getStatus() {
    return status;
  }

  /**
   * @return valor de prioridad actual (mayor valor = mayor prioridad)
   */
  public int getPriority() {
    return priority;
  }

  /**
   * Establece la prioridad del pedido. Usado por {@code PriorityCalculator}
   * para asignar un nivel de urgencia según distancia, tráfico y otros factores.
   *
   * @param priority valor no negativo de prioridad
   * @throws IllegalArgumentException si {@code priority} es negativo
   */
  public void setPriority(int priority) {
    if (priority < 0)
      throw new IllegalArgumentException("priority cannot be negative");
    this.priority = priority;
  }

  /**
   * @return tiempo estimado de entrega en minutos
   */
  public double getEstimatedTime() {
    return estimatedTime;
  }

  /**
   * Establece el tiempo estimado de entrega.
   *
   * @param estimatedTime tiempo estimado en minutos
   * @throws IllegalArgumentException si {@code estimatedTime} es negativo
   */
  public void setEstimatedTime(double estimatedTime) {
    if (estimatedTime < 0)
      throw new IllegalArgumentException("estimatedTime cannot be negative");
    this.estimatedTime = estimatedTime;
  }

  /**
   * @imestamp de creación del pedido
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * @return timestamp de entrega, o {@code null} si aún no se ha entregado
   */
  public LocalDateTime getDeliveredAt() {
    return deliveredAt;
  }

  /**
   * @return {@code true} si el pedido ya fue entregado
   */
  public boolean isDelivered() {
    return status == OrderStatus.DELIVERED;
  }

  /**
   * Avanza el estado a {@link OrderStatus#PREPARING}.
   * Solo puede invocarse desde {@link OrderStatus#PENDING}.
   *
   * @throws IllegalStateException si el estado actual no es PENDING
   */
  public void markAsPreparing() {
    if (this.status != OrderStatus.PENDING)
      throw new IllegalStateException("Only PENDING orders can be marked as PREPARING");
    this.status = OrderStatus.PREPARING;
  }

  /**
   * Avanza el estado a {@link OrderStatus#IN_TRANSIT}.
   * Solo puede invocarse desde {@link OrderStatus#PREPARING}.
   *
   * @throws IllegalStateException si el estado actual no es PREPARING
   */
  public void markAsInTransit() {
    if (this.status != OrderStatus.PREPARING)
      throw new IllegalStateException("Only PREPARING orders can be marked as IN_TRANSIT");
    this.status = OrderStatus.IN_TRANSIT;
  }

  /**
   * Avanza el estado a {@link OrderStatus#DELIVERED} y registra el
   * timestamp de entrega. Solo puede invocarse desde
   * {@link OrderStatus#IN_TRANSIT}.
   *
   * @throws IllegalStateException si el estado actual no es IN_TRANSIT
   */
  public void markAsDelivered() {
    if (this.status != OrderStatus.IN_TRANSIT)
      throw new IllegalStateException("Only IN_TRANSIT orders can be marked as DELIVERED");
    this.status = OrderStatus.DELIVERED;
    this.deliveredAt = LocalDateTime.now();
  }

  /**
   * Cancela el pedido. Válido desde cualquier estado excepto
   * {@link OrderStatus#DELIVERED}.
   *
   * @throws IllegalStateException si el pedido ya fue entregado
   */
  public void cancel() {
    if (this.status == OrderStatus.DELIVERED)
      throw new IllegalStateException("DELIVERED orders cannot be cancelled");
    this.status = OrderStatus.CANCELLED;
  }

  /**
   * @return representación textual del pedido
   */
  @Override
  public String toString() {
    return "Order{id='" + id + "', client=" + client.getName() + ", status=" + status + ", priority=" + priority + "}";
  }

  /**
   * Compara pedidos por su identificador único.
   *
   * @param o objeto a comparar
   * @return {@code true} si ambos pedidos tienen el mismo {@code id}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Order))
      return false;
    Order order = (Order) o;
    return Objects.equals(id, order.id);
  }

  /**
   * @return hash basado en el identificador único
   */
  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
