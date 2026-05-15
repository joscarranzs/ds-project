package com.ds.core.managers;

import com.ds.core.enums.PriorityLevel;
import com.ds.core.models.DeliveryDriver;
import com.ds.core.models.Order;
import com.ds.core.models.OrderStatus;
import com.ds.core.priority.PriorityManager;
import com.ds.core.services.DeliveryService;
import com.ds.core.services.EstimatedTimeService;
import com.ds.core.services.OrderService;
import java.util.List;
import java.util.Optional;

/**
 * Administrador de pedidos activos del sistema.
 * <p>
 * Coordina el servicio de pedidos, la cola de prioridad y
 * el servicio de entregas para gestionar el ciclo de vida
 * completo de un pedido desde su creacion hasta la entrega.
 * </p>
 */
public class OrderManager {

  private final OrderService orderService;
  private final PriorityManager priorityManager;
  private final DeliveryService deliveryService;
  private final EstimatedTimeService estimatedTimeService;

  /**
   * Construye el administrador con los servicios necesarios.
   *
   * @param orderService         servicio de pedidos
   * @param priorityManager      gestor de prioridades
   * @param deliveryService      servicio de entregas
   * @param estimatedTimeService servicio de estimacion de tiempo
   */
  public OrderManager(OrderService orderService, PriorityManager priorityManager,
      DeliveryService deliveryService, EstimatedTimeService estimatedTimeService) {
    if (orderService == null)
      throw new IllegalArgumentException("orderService cannot be null");
    if (priorityManager == null)
      throw new IllegalArgumentException("priorityManager cannot be null");
    if (deliveryService == null)
      throw new IllegalArgumentException("deliveryService cannot be null");
    if (estimatedTimeService == null)
      throw new IllegalArgumentException("estimatedTimeService cannot be null");
    this.orderService = orderService;
    this.priorityManager = priorityManager;
    this.deliveryService = deliveryService;
    this.estimatedTimeService = estimatedTimeService;
  }

  /**
   * Procesa el siguiente pedido de la cola de prioridad:
   * asigna un repartidor disponible y avanza el estado.
   *
   * @return Optional con el pedido procesado
   */
  public Optional<Order> processNextOrder() {
    Order next = priorityManager.pollNextOrder();
    if (next == null)
      return Optional.empty();

    Optional<DeliveryDriver> driver = deliveryService.assignOrder(next);
    if (driver.isPresent()) {
      orderService.markAsPreparing(next.getId());
      return Optional.of(next);
    }

    priorityManager.addOrder(next);
    return Optional.empty();
  }

  /**
   * Avanza un pedido a IN_TRANSIT.
   *
   * @param orderId identificador del pedido
   * @return Optional con el pedido actualizado
   */
  public Optional<Order> startDelivery(String orderId) {
    return orderService.markAsInTransit(orderId);
  }

  /**
   * Finaliza la entrega de un pedido: lo marca como entregado
   * y completa la asignacion del repartidor.
   *
   * @param orderId    identificador del pedido
   * @param driverName nombre del repartidor
   * @return Optional con el pedido entregado
   */
  public Optional<Order> completeDelivery(String orderId, String driverName) {
    Optional<Order> order = orderService.markAsDelivered(orderId);
    deliveryService.completeDelivery(driverName);
    return order;
  }

  /**
   * Recalcula las prioridades de todos los pedidos activos.
   */
  public void recalculatePriorities() {
    priorityManager.recalculateAll();
  }

  /**
   * Retorna los pedidos pendientes ordenados por prioridad.
   *
   * @return lista de pedidos pendientes
   */
  public List<Order> getPendingOrders() {
    return orderService.getOrdersByStatus(OrderStatus.PENDING);
  }

  /**
   * Retorna los pedidos en preparacion.
   *
   * @return lista de pedidos en preparacion
   */
  public List<Order> getPreparingOrders() {
    return orderService.getOrdersByStatus(OrderStatus.PREPARING);
  }

  /**
   * Retorna los pedidos en transito.
   *
   * @return lista de pedidos en transito
   */
  public List<Order> getInTransitOrders() {
    return orderService.getOrdersByStatus(OrderStatus.IN_TRANSIT);
  }

  /**
   * Retorna los pedidos por nivel de prioridad.
   *
   * @param level nivel de prioridad
   * @return lista de pedidos en ese nivel
   */
  public List<Order> getOrdersByPriorityLevel(PriorityLevel level) {
    return priorityManager.getOrdersByLevel(level);
  }

  /**
   * @return cantidad de pedidos activos (PENDING + PREPARING + IN_TRANSIT)
   */
  public int getActiveCount() {
    return getPendingOrders().size() + getPreparingOrders().size() + getInTransitOrders().size();
  }

  /**
   * @return siguiente pedido en la cola de prioridad sin removerlo
   */
  public Optional<Order> peekNext() {
    return Optional.ofNullable(priorityManager.peekNextOrder());
  }
}
