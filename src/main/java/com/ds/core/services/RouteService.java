package com.ds.core.services;

import com.ds.core.algorithms.RouteOptimizer;
import com.ds.core.graph.Graph;
import com.ds.core.models.Node;
import com.ds.core.models.Route;
import com.ds.core.validators.RouteValidator;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de gestion de rutas.
 * <p>
 * Coordina el calculo de rutas optimas utilizando los algoritmos
 * de busqueda y valida los resultados contra el grafo del sistema.
 * </p>
 */
public class RouteService {

  private final Graph graph;
  private final RouteOptimizer optimizer;

  /**
   * Construye el servicio con un grafo y un optimizador de rutas.
   *
   * @param graph     grafo del sistema
   * @param optimizer optimizador de rutas
   */
  public RouteService(Graph graph, RouteOptimizer optimizer) {
    if (graph == null) throw new IllegalArgumentException("graph cannot be null");
    if (optimizer == null) throw new IllegalArgumentException("optimizer cannot be null");
    this.graph = graph;
    this.optimizer = optimizer;
  }

  /**
   * Calcula la ruta mas corta en distancia entre dos nodos.
   *
   * @param originId      identificador del nodo origen
   * @param destinationId identificador del nodo destino
   * @return ruta optima, o {@code Optional#empty()} si no existe
   */
  public Optional<Route> findShortestRoute(String originId, String destinationId) {
    Route route = optimizer.optimizeByDistance(originId, destinationId);
    return Optional.ofNullable(route);
  }

  /**
   * Calcula la ruta mas rapida en tiempo entre dos nodos.
   *
   * @param originId      identificador del nodo origen
   * @param destinationId identificador del nodo destino
   * @return ruta optima, o {@code Optional#empty()} si no existe
   */
  public Optional<Route> findFastestRoute(String originId, String destinationId) {
    Route route = optimizer.optimizeByTime(originId, destinationId);
    return Optional.ofNullable(route);
  }

  /**
   * Verifica si existe conexion entre dos nodos.
   *
   * @param originId      identificador del nodo origen
   * @param destinationId identificador del nodo destino
   * @return {@code true} si hay al menos un camino
   */
  public boolean hasConnection(String originId, String destinationId) {
    return optimizer.hasConnection(originId, destinationId);
  }

  /**
   * Retorna todos los nodos del grafo.
   *
   * @return lista de nodos
   */
  public List<Node> getAllNodes() {
    return List.copyOf(graph.getNodes());
  }

  /**
   * Busca un nodo por su identificador.
   *
   * @param nodeId identificador del nodo
   * @return Optional con el nodo encontrado
   */
  public Optional<Node> findNodeById(String nodeId) {
    return Optional.ofNullable(graph.getNode(nodeId));
  }

  /**
   * Valida que una ruta sea consistente con el grafo actual.
   *
   * @param route ruta a validar
   * @throws IllegalArgumentException si la ruta es invalida
   */
  public void validateRoute(Route route) {
    RouteValidator.validateAgainstGraph(route, graph);
  }
}