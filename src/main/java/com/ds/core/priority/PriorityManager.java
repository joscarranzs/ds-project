package com.ds.core.priority;

import com.ds.core.enums.PriorityLevel;
import com.ds.core.models.Order;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Gestiona las prioridades activas del sistema.
 * <p>
 * Mantiene una cola de prioridad con los pedidos activos,
 * permitiendo reordenarlos cuando cambian las condiciones
 * (trafico, nuevos pedidos, actualizaciones de prioridad).
 * </p>
 */
public class PriorityManager {

  private final PriorityQueue<Order> priorityQueue;
  private final PriorityCalculator calculator;

  /**
   * Construye el gestor de prioridades con el calculador dado.
   *
   * @param calculator calculador de prioridad
   */
  public PriorityManager(PriorityCalculator calculator) {
    if (calculator == null)
      throw new IllegalArgumentException("calculator cannot be null");
    this.calculator = calculator;
    this.priorityQueue = new PriorityQueue<>(
        Comparator.comparingInt(Order::getPriority).reversed()
            .thenComparing(Order::getCreatedAt));
  }

  /**
   * Agrega un pedido a la cola de prioridad.
   * Calcula y asigna la prioridad automaticamente.
   *
   * @param order pedido a agregar
   */
  public void addOrder(Order order) {
    if (order == null)
      throw new IllegalArgumentException("order cannot be null");
    calculator.calculateAndAssign(order);
    priorityQueue.offer(order);
  }

  /**
   * Obtiene y remueve el pedido con mayor prioridad.
   *
   * @return pedido de mayor prioridad, o {@code null} si la cola esta vacia
   */
  public Order pollNextOrder() {
    return priorityQueue.poll();
  }

  /**
   * Obtiene el pedido con mayor prioridad sin removerlo.
   *
   * @return pedido de mayor prioridad, o {@code null} si la cola esta vacia
   */
  public Order peekNextOrder() {
    return priorityQueue.peek();
  }

  /**
   * Recalcula la prioridad de todos los pedidos activos y
   * reordena la cola. Util cuando cambian las condiciones
   * del sistema (trafico, nuevos pedidos, etc.).
   */
  public void recalculateAll() {
    List<Order> orders = priorityQueue.stream()
        .peek(calculator::calculateAndAssign)
        .collect(Collectors.toList());
    priorityQueue.clear();
    priorityQueue.addAll(orders);
  }

  /**
   * Elimina un pedido especifico de la cola de prioridad.
   *
   * @param orderId identificador del pedido a eliminar
   */
  public void removeOrder(String orderId) {
    priorityQueue.removeIf(o -> o.getId().equals(orderId));
  }

  /**
   * Retorna todos los pedidos ordenados por prioridad descendente.
   *
   * @return lista de pedidos ordenados
   */
  public List<Order> getOrdersByPriority() {
    return priorityQueue.stream()
        .sorted(Comparator.comparingInt(Order::getPriority).reversed())
        .collect(Collectors.toList());
  }

  /**
   * Retorna los pedidos que corresponden a un nivel de prioridad especifico.
   *
   * @param level nivel de prioridad deseado
   * @return lista de pedidos en ese nivel
   */
  public List<Order> getOrdersByLevel(PriorityLevel level) {
    return priorityQueue.stream()
        .filter(o -> calculator.toLevel(o.getPriority()) == level)
        .collect(Collectors.toList());
  }

  /**
   * @return cantidad de pedidos en la cola
   */
  public int size() {
    return priorityQueue.size();
  }

  /**
   * @return {@code true} si no hay pedidos en la cola
   */
  public boolean isEmpty() {
    return priorityQueue.isEmpty();
  }

  /**
   * Limpia la cola de prioridad.
   */
  public void clear() {
    priorityQueue.clear();
  }
}
