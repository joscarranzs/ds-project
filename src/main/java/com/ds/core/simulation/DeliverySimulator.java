package com.ds.core.simulation;

import com.ds.core.managers.SystemManager;
import com.ds.core.models.Node;
import com.ds.core.models.Order;
import com.ds.core.utils.IdGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Simula el flujo completo de entregas para pruebas y validacion.
 * <p>
 * Genera pedidos aleatorios, los procesa segun la cola de prioridad,
 * asigna repartidores y simula la entrega paso a paso, permitiendo
 * verificar el comportamiento del sistema en distintos escenarios.
 * </p>
 */
public class DeliverySimulator {

  private final SystemManager systemManager;
  private final Random random;
  private boolean running;

  /**
   * Construye el simulador asociado al gestor del sistema.
   *
   * @param systemManager gestor principal del sistema
   */
  public DeliverySimulator(SystemManager systemManager) {
    if (systemManager == null)
      throw new IllegalArgumentException("systemManager cannot be null");
    this.systemManager = systemManager;
    this.random = new Random();
    this.running = false;
  }

  /**
   * Inicia la simulacion con los parametros dados.
   *
   * @param numOrders  cantidad de pedidos a simular
   * @param numDrivers cantidad de repartidores a registrar
   * @param nodes      lista de nodos disponibles para origen/destino
   */
  public void start(int numOrders, int numDrivers, List<Node> nodes) {
    if (nodes == null || nodes.size() < 2)
      throw new IllegalArgumentException("At least 2 nodes required");
    running = true;

    registerDrivers(numDrivers, nodes);
    List<Order> orders = generateOrders(numOrders, nodes);

    for (Order order : orders) {
      if (!running)
        break;
      simulateOrderLifecycle(order);
    }

    running = false;
  }

  /**
   * Detiene la simulacion en curso.
   */
  public void stop() {
    this.running = false;
  }

  /**
   * @return {@code true} si la simulacion esta activa
   */
  public boolean isRunning() {
    return running;
  }

  /**
   * Registra repartidores en ubicaciones aleatorias.
   *
   * @param count cantidad de repartidores a crear
   * @param nodes lista de nodos disponibles como ubicaciones
   */
  private void registerDrivers(int count, List<Node> nodes) {
    for (int i = 0; i < count; i++) {
      Node location = nodes.get(random.nextInt(nodes.size()));
      systemManager.getDeliveryService().registerDriver("Driver-" + (i + 1), location);
    }
  }

  /**
   * Genera pedidos aleatorios con origen y destino distintos.
   *
   * @param count cantidad de pedidos a generar
   * @param nodes lista de nodos disponibles
   * @return lista de pedidos generados
   */
  private List<Order> generateOrders(int count, List<Node> nodes) {
    List<Order> orders = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      Node origin = nodes.get(random.nextInt(nodes.size()));
      Node destination;
      do {
        destination = nodes.get(random.nextInt(nodes.size()));
      } while (destination.equals(origin));

      String clientName = "Client-" + IdGenerator.newClientId();
      Order order = systemManager.createFullOrder(
          clientName, "+555" + (1000 + i), "Address " + (i + 1), origin, destination);
      orders.add(order);
    }
    return orders;
  }

  /**
   * Simula el ciclo de vida completo de un pedido:
   * PREPARING → IN_TRANSIT → DELIVERED con delays intermedios.
   *
   * @param order pedido a simular
   */
  private void simulateOrderLifecycle(Order order) {
    systemManager.getOrderService().markAsPreparing(order.getId());
    simulateDelay(500);

    systemManager.getOrderService().markAsInTransit(order.getId());
    simulateDelay(500);

    systemManager.getOrderService().markAsDelivered(order.getId());
  }

  /**
   * Pausa la ejecucion por la cantidad de milisegundos indicada
   * para simular tiempos de procesamiento reales.
   *
   * @param millis milisegundos de espera
   */
  private void simulateDelay(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
