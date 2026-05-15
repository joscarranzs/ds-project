package com.ds.core.validators;

import com.ds.core.models.Order;
import com.ds.core.models.OrderStatus;
import java.util.List;

/**
 * Validador de pedidos.
 * <p>
 * Verifica que los datos de un {@link Order} sean consistentes
 * antes de ser procesados por el sistema: campos obligatorios,
 * transiciones de estado validas y rangos aceptables.
 * </p>
 */
public class OrderValidator {

  private static final List<OrderStatus> CANCELLABLE_STATUSES = List.of(
      OrderStatus.PENDING, OrderStatus.PREPARING, OrderStatus.IN_TRANSIT
  );

  private OrderValidator() {
  }

  /**
   * Valida que los campos esenciales del pedido no sean nulos.
   *
   * @param order pedido a validar
   * @throws IllegalArgumentException si el pedido es invalido
   */
  public static void validate(Order order) {
    if (order == null) throw new IllegalArgumentException("Order cannot be null");
    if (order.getId() == null || order.getId().isBlank())
      throw new IllegalArgumentException("Order id cannot be blank");
    if (order.getClient() == null)
      throw new IllegalArgumentException("Order client cannot be null");
    if (order.getOrigin() == null)
      throw new IllegalArgumentException("Order origin cannot be null");
    if (order.getDestination() == null)
      throw new IllegalArgumentException("Order destination cannot be null");
    if (order.getOrigin().equals(order.getDestination()))
      throw new IllegalArgumentException("Order origin and destination must be different");
    if (order.getPriority() < 0 || order.getPriority() > 100)
      throw new IllegalArgumentException("Order priority must be between 0 and 100");
    if (order.getEstimatedTime() < 0)
      throw new IllegalArgumentException("Order estimated time cannot be negative");
  }

  /**
   * Verifica si la transicion al nuevo estado es valida segun el estado actual.
   *
   * @param current estado actual del pedido
   * @param next    estado al que se desea transicionar
   * @return {@code true} si la transicion es permitida
   */
  public static boolean isValidTransition(OrderStatus current, OrderStatus next) {
    if (current == null || next == null) return false;
    switch (current) {
      case PENDING:
        return next == OrderStatus.PREPARING || next == OrderStatus.CANCELLED;
      case PREPARING:
        return next == OrderStatus.IN_TRANSIT || next == OrderStatus.CANCELLED;
      case IN_TRANSIT:
        return next == OrderStatus.DELIVERED || next == OrderStatus.CANCELLED;
      case DELIVERED:
        return false;
      case CANCELLED:
        return false;
      default:
        return false;
    }
  }

  /**
   * Verifica si el pedido puede ser cancelado segun su estado actual.
   *
   * @param status estado actual del pedido
   * @return {@code true} si el pedido puede cancelarse
   */
  public static boolean isCancellable(OrderStatus status) {
    return CANCELLABLE_STATUSES.contains(status);
  }
}