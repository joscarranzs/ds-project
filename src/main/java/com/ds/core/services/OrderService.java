package com.ds.core.services;

import com.ds.core.models.Client;
import com.ds.core.models.Node;
import com.ds.core.models.Order;
import com.ds.core.models.OrderStatus;
import com.ds.core.priority.PriorityManager;
import com.ds.core.utils.IdGenerator;
import com.ds.core.validators.OrderValidator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio de gestion de pedidos.
 * <p>
 * Proporciona operaciones CRUD sobre pedidos, control de cambios
 * de estado y coordinacion con el sistema de prioridades.
 * </p>
 */
public class OrderService {

  private final List<Order> orders;
  private final PriorityManager priorityManager;

  /**
   * Construye el servicio con un gestor de prioridades.
   *
   * @param priorityManager gestor de prioridades del sistema
   */
  public OrderService(PriorityManager priorityManager) {
    if (priorityManager == null)
      throw new IllegalArgumentException("priorityManager cannot be null");
    this.orders = new ArrayList<>();
    this.priorityManager = priorityManager;
  }

  /**
   * Crea un nuevo pedido, lo valida, asigna ID, calcula prioridad
   * y lo registra en el sistema.
   *
   * @param client      cliente que realiza el pedido
   * @param origin      nodo de origen
   * @param destination nodo de destino
   * @return pedido creado
   */
  public Order createOrder(Client client, Node origin, Node destination) {
    String id = IdGenerator.newOrderId();
    Order order = new Order(id, client, origin, destination);
    OrderValidator.validate(order);
    orders.add(order);
    priorityManager.addOrder(order);
    return order;
  }

  /**
   * Avanza el estado del pedido a PREPARING.
   *
   * @param orderId identificador del pedido
   * @return el pedido actualizado
   */
  public Optional<Order> markAsPreparing(String orderId) {
    return findById(orderId).map(order -> {
      order.markAsPreparing();
      return order;
    });
  }

  /**
   * Avanza el estado del pedido a IN_TRANSIT.
   *
   * @param orderId identificador del pedido
   * @return el pedido actualizado
   */
  public Optional<Order> markAsInTransit(String orderId) {
    return findById(orderId).map(order -> {
      order.markAsInTransit();
      return order;
    });
  }

  /**
   * Avanza el estado del pedido a DELIVERED y lo remueve de la cola
   * de prioridad.
   *
   * @param orderId identificador del pedido
   * @return el pedido actualizado
   */
  public Optional<Order> markAsDelivered(String orderId) {
    return findById(orderId).map(order -> {
      order.markAsDelivered();
      priorityManager.removeOrder(orderId);
      return order;
    });
  }

  /**
   * Cancela un pedido y lo remueve de la cola de prioridad.
   *
   * @param orderId identificador del pedido
   * @return el pedido cancelado
   */
  public Optional<Order> cancelOrder(String orderId) {
    return findById(orderId).map(order -> {
      order.cancel();
      priorityManager.removeOrder(orderId);
      return order;
    });
  }

  /**
   * Busca un pedido por su identificador.
   *
   * @param orderId identificador del pedido
   * @return Optional con el pedido encontrado
   */
  public Optional<Order> findById(String orderId) {
    return orders.stream()
        .filter(o -> o.getId().equals(orderId))
        .findFirst();
  }

  /**
   * Retorna todos los pedidos registrados.
   *
   * @return lista de pedidos
   */
  public List<Order> getAllOrders() {
    return new ArrayList<>(orders);
  }

  /**
   * Retorna pedidos filtrados por estado.
   *
   * @param status estado deseado
   * @return lista de pedidos en ese estado
   */
  public List<Order> getOrdersByStatus(OrderStatus status) {
    return orders.stream()
        .filter(o -> o.getStatus() == status)
        .collect(Collectors.toList());
  }

  /**
   * Retorna pedidos creados dentro de un rango de fechas.
   *
   * @param from fecha inicial
   * @param to   fecha final
   * @return lista de pedidos en el rango
   */
  public List<Order> getOrdersBetween(LocalDateTime from, LocalDateTime to) {
    return orders.stream()
        .filter(o -> !o.getCreatedAt().isBefore(from) && !o.getCreatedAt().isAfter(to))
        .collect(Collectors.toList());
  }

  /**
   * @return cantidad total de pedidos
   */
  public int count() {
    return orders.size();
  }
}
