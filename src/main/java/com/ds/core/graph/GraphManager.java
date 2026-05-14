package com.ds.core.graph;

import com.ds.core.enums.RouteStatus;
import com.ds.core.models.Edge;
import com.ds.core.models.Node;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Administrador del grafo de rutas.
 * <p>
 * Proporciona operaciones de alto nivel sobre el {@link Graph}:
 * actualizacion de trafico, bloqueo y desbloqueo de rutas, y
 * modificacion dinamica del estado de las aristas.
 * </p>
 */
public class GraphManager {

  private final Graph graph;

  /**
   * Construye un administrador asociado al grafo dado.
   *
   * @param graph grafo a administrar
   * @throws IllegalArgumentException si {@code graph} es {@code null}
   */
  public GraphManager(Graph graph) {
    if (graph == null)
      throw new IllegalArgumentException("graph cannot be null");
    this.graph = graph;
  }

  /**
   * Retorna el grafo administrado.
   *
   * @return el grafo interno
   */
  public Graph getGraph() {
    return graph;
  }

  /**
   * Actualiza el estado de todas las aristas que conectan dos nodos.
   *
   * @param fromId identificador del nodo origen
   * @param toId   identificador del nodo destino
   * @param status nuevo estado de la ruta
   */
  public void updateRouteStatus(String fromId, String toId, RouteStatus status) {
    List<Edge> edges = graph.getEdgesFrom(fromId).stream()
        .filter(e -> e.getTo().getId().equals(toId))
        .collect(Collectors.toList());
    edges.forEach(e -> e.setStatus(status));
  }

  /**
   * Bloquea todas las rutas entre dos nodos, marcandolas como
   * {@link RouteStatus#BLOCKED}.
   *
   * @param fromId identificador del nodo origen
   * @param toId   identificador del nodo destino
   */
  public void blockRoute(String fromId, String toId) {
    updateRouteStatus(fromId, toId, RouteStatus.BLOCKED);
  }

  /**
   * Desbloquea todas las rutas entre dos nodos, restaurandolas a
   * {@link RouteStatus#OPEN}.
   *
   * @param fromId identificador del nodo origen
   * @param toId   identificador del nodo destino
   */
  public void unblockRoute(String fromId, String toId) {
    updateRouteStatus(fromId, toId, RouteStatus.OPEN);
  }

  /**
   * Marca una ruta como congestionada ({@link RouteStatus#CONGESTED}).
   *
   * @param fromId identificador del nodo origen
   * @param toId   identificador del nodo destino
   */
  public void congestRoute(String fromId, String toId) {
    updateRouteStatus(fromId, toId, RouteStatus.CONGESTED);
  }

  /**
   * Verifica si existe al menos una ruta abierta entre dos nodos.
   *
   * @param fromId identificador del nodo origen
   * @param toId   identificador del nodo destino
   * @return {@code true} si existe una conexion con estado OPEN
   */
  public boolean hasOpenRoute(String fromId, String toId) {
    return graph.getEdgesFrom(fromId).stream()
        .anyMatch(e -> e.getTo().getId().equals(toId) && e.getStatus() == RouteStatus.OPEN);
  }

  /**
   * Retorna las aristas desde un nodo que no esten bloqueadas.
   *
   * @param nodeId identificador del nodo origen
   * @return lista de aristas disponibles (OPEN o CONGESTED)
   */
  public List<Edge> getAvailableEdges(String nodeId) {
    return graph.getEdgesFrom(nodeId).stream()
        .filter(e -> e.getStatus() != RouteStatus.BLOCKED)
        .collect(Collectors.toList());
  }

  /**
   * Agrega una nueva arista al grafo administrado.
   *
   * @param edge arista a agregar
   */
  public void addEdge(Edge edge) {
    graph.addEdge(edge);
  }

  /**
   * Agrega un nuevo nodo al grafo administrado.
   *
   * @param node nodo a agregar
   */
  public void addNode(Node node) {
    graph.addNode(node);
  }

  /**
   * Elimina un nodo y sus conexiones del grafo administrado.
   *
   * @param nodeId identificador del nodo a eliminar
   */
  public void removeNode(String nodeId) {
    graph.removeNode(nodeId);
  }
}
