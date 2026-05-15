package com.ds.core.validators;

import com.ds.core.graph.Graph;
import com.ds.core.models.Node;
import com.ds.core.models.Route;
import java.util.List;

/**
 * Validador de rutas.
 * <p>
 * Verifica que una {@link Route} sea consistente con el {@link Graph}
 * del sistema: que los nodos existan, que las conexiones sean validas
 * y que los valores numericos esten dentro de rangos aceptables.
 * </p>
 */
public class RouteValidator {

  private RouteValidator() {
  }

  /**
   * Valida que los campos de la ruta sean consistentes.
   *
   * @param route ruta a validar
   * @throws IllegalArgumentException si la ruta es invalida
   */
  public static void validate(Route route) {
    if (route == null)
      throw new IllegalArgumentException("Route cannot be null");
    if (route.getOrigin() == null)
      throw new IllegalArgumentException("Route origin cannot be null");
    if (route.getDestination() == null)
      throw new IllegalArgumentException("Route destination cannot be null");
    if (route.getPath() == null || route.getPath().isEmpty())
      throw new IllegalArgumentException("Route path cannot be null or empty");
    if (route.getTotalDistance() < 0)
      throw new IllegalArgumentException("Route total distance cannot be negative");
    if (route.getTotalTime() < 0)
      throw new IllegalArgumentException("Route total time cannot be negative");
  }

  /**
   * Valida que la ruta sea coherente con el grafo: origen, destino y
   * todos los nodos del path deben existir en el grafo.
   *
   * @param route ruta a validar
   * @param graph grafo contra el cual validar
   * @throws IllegalArgumentException si algun nodo no existe en el grafo
   */
  public static void validateAgainstGraph(Route route, Graph graph) {
    if (graph == null)
      throw new IllegalArgumentException("Graph cannot be null");
    validate(route);

    if (!graph.hasNode(route.getOrigin().getId()))
      throw new IllegalArgumentException("Route origin node not found in graph: " + route.getOrigin().getId());
    if (!graph.hasNode(route.getDestination().getId()))
      throw new IllegalArgumentException(
          "Route destination node not found in graph: " + route.getDestination().getId());

    for (Node node : route.getPath()) {
      if (!graph.hasNode(node.getId()))
        throw new IllegalArgumentException("Node in route path not found in graph: " + node.getId());
    }
  }

  /**
   * Valida que exista una conexion directa entre dos nodos en el grafo.
   *
   * @param graph  grafo donde verificar la conexion
   * @param fromId identificador del nodo origen
   * @param toId   identificador del nodo destino
   * @throws IllegalArgumentException si no existe conexion
   */
  public static void validateConnection(Graph graph, String fromId, String toId) {
    if (!graph.hasNode(fromId))
      throw new IllegalArgumentException("Origin node not found: " + fromId);
    if (!graph.hasNode(toId))
      throw new IllegalArgumentException("Destination node not found: " + toId);
    if (!graph.hasEdge(fromId, toId))
      throw new IllegalArgumentException("No direct edge between " + fromId + " and " + toId);
  }

  /**
   * Verifica que el path de la ruta sea secuencialmente valido:
   * cada par de nodos consecutivos debe tener una arista en el grafo.
   *
   * @param graph grafo contra el cual validar
   * @param path  lista ordenada de nodos
   * @return {@code true} si todas las conexiones del path existen
   */
  public static boolean isPathValid(Graph graph, List<Node> path) {
    if (path == null || path.size() < 2)
      return false;
    for (int i = 0; i < path.size() - 1; i++) {
      String from = path.get(i).getId();
      String to = path.get(i + 1).getId();
      if (!graph.hasEdge(from, to))
        return false;
    }
    return true;
  }
}
