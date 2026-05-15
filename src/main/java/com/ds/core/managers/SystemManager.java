package com.ds.core.managers;

import com.ds.core.algorithms.RouteOptimizer;
import com.ds.core.graph.Graph;
import com.ds.core.graph.GraphBuilder;
import com.ds.core.graph.GraphManager;
import com.ds.core.models.Client;
import com.ds.core.models.Node;
import com.ds.core.models.Order;
import com.ds.core.priority.PriorityCalculator;
import com.ds.core.priority.PriorityManager;
import com.ds.core.priority.PriorityRules;
import com.ds.core.services.ClientService;
import com.ds.core.services.DeliveryService;
import com.ds.core.services.EstimatedTimeService;
import com.ds.core.services.OrderService;
import com.ds.core.services.RouteService;
import java.util.List;

/**
 * Coordinador principal de todos los modulos del core.
 * <p>
 * Actua como fachada (facade) del sistema, inicializando y
 * orquestando los modulos de grafo, servicios, prioridad y
 * algoritmos. Proporciona un punto de entrada unico para
 * la capa de presentacion (controllers / UI).
 * </p>
 */
public class SystemManager {

  private final Graph graph;
  private final GraphManager graphManager;
  private final RouteOptimizer optimizer;

  private final PriorityRules priorityRules;
  private final PriorityCalculator priorityCalculator;
  private final PriorityManager priorityManager;

  private final OrderService orderService;
  private final RouteService routeService;
  private final ClientService clientService;
  private final DeliveryService deliveryService;
  private final EstimatedTimeService estimatedTimeService;

  /**
   * Construye el SystemManager inicializando todos los modulos
   * con sus valores por defecto.
   */
  public SystemManager() {
    this.graph = new Graph();
    this.graphManager = new GraphManager(graph);
    this.optimizer = new RouteOptimizer(graph);

    this.priorityRules = new PriorityRules();
    this.priorityCalculator = new PriorityCalculator(priorityRules);
    this.priorityManager = new PriorityManager(priorityCalculator);

    this.orderService = new OrderService(priorityManager);
    this.routeService = new RouteService(graph, optimizer);
    this.clientService = new ClientService();
    this.deliveryService = new DeliveryService();
    this.estimatedTimeService = new EstimatedTimeService(optimizer, graphManager);
  }

  /**
   * @return grafo del sistema
   */
  public Graph getGraph() {
    return graph;
  }

  /**
   * @return administrador del grafo
   */
  public GraphManager getGraphManager() {
    return graphManager;
  }

  /**
   * @return optimizador de rutas
   */
  public RouteOptimizer getRouteOptimizer() {
    return optimizer;
  }

  /**
   * @return servicio de pedidos
   */
  public OrderService getOrderService() {
    return orderService;
  }

  /**
   * @return servicio de rutas
   */
  public RouteService getRouteService() {
    return routeService;
  }

  /**
   * @return servicio de clientes
   */
  public ClientService getClientService() {
    return clientService;
  }

  /**
   * @return servicio de entregas
   */
  public DeliveryService getDeliveryService() {
    return deliveryService;
  }

  /**
   * @return servicio de estimacion de tiempo
   */
  public EstimatedTimeService getEstimatedTimeService() {
    return estimatedTimeService;
  }

  /**
   * @return gestor de prioridades
   */
  public PriorityManager getPriorityManager() {
    return priorityManager;
  }

  /**
   * @return calculador de prioridad
   */
  public PriorityCalculator getPriorityCalculator() {
    return priorityCalculator;
  }

  /**
   * @return reglas de prioridad
   */
  public PriorityRules getPriorityRules() {
    return priorityRules;
  }

  /**
   * Inicializa el grafo con nodos y aristas por defecto.
   */
  public void initializeDefaultGraph() {
    graph.clear();
    GraphBuilder builder = new GraphBuilder();
    builder.addNodes(List.of()).addEdges(List.of());
  }

  /**
   * Crea un pedido completo: registra cliente, calcula ruta,
   * estima tiempo y encola en prioridad.
   *
   * @param clientName  nombre del cliente
   * @param phone       telefono del cliente
   * @param address     direccion del cliente
   * @param origin      nodo de origen
   * @param destination nodo de destino
   * @return pedido creado
   */
  public Order createFullOrder(String clientName, String phone, String address,
      Node origin, Node destination) {
    Client client = clientService.registerClient(clientName, phone, address, origin);
    Order order = orderService.createOrder(client, origin, destination);
    estimatedTimeService.estimateAndAssign(order);
    return order;
  }
}
