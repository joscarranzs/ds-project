package com.ds.core.algorithms;

import com.ds.core.graph.Graph;
import com.ds.core.models.Node;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementacion del algoritmo DFS (Depth First Search) para
 * recorrido en profundidad sobre el grafo.
 * <p>
 * Util para explorar rutas profundas, simular trayectorias
 * y descubrir todas las conexiones posibles desde un punto de partida.
 * </p>
 */
public class DFSAlgorithm {

  private final Graph graph;

  /**
   * Construye el algoritmo asociado a un grafo.
   *
   * @param graph grafo sobre el cual operar
   */
  public DFSAlgorithm(Graph graph) {
    if (graph == null)
      throw new IllegalArgumentException("graph cannot be null");
    this.graph = graph;
  }

  /**
   * Realiza un recorrido DFS desde el nodo origen y retorna todos
   * los nodos alcanzables en el orden en que fueron visitados.
   *
   * @param originId identificador del nodo origen
   * @return lista de nodos en orden de visita DFS
   */
  public List<Node> traverse(String originId) {
    if (!graph.hasNode(originId))
      return Collections.emptyList();

    Set<String> visited = new HashSet<>();
    List<Node> result = new ArrayList<>();
    dfsRecursive(originId, visited, result);
    return result;
  }

  private void dfsRecursive(String nodeId, Set<String> visited, List<Node> result) {
    visited.add(nodeId);
    result.add(graph.getNode(nodeId));

    for (Node neighbor : graph.getNeighbors(nodeId)) {
      if (!visited.contains(neighbor.getId())) {
        dfsRecursive(neighbor.getId(), visited, result);
      }
    }
  }

  /**
   * Busca un camino entre dos nodos usando DFS.
   * No necesariamente encuentra el camino mas corto.
   *
   * @param originId      identificador del nodo origen
   * @param destinationId identificador del nodo destino
   * @return lista de nodos desde origen a destino, o lista vacia si no existe
   */
  public List<Node> findPath(String originId, String destinationId) {
    if (!graph.hasNode(originId) || !graph.hasNode(destinationId))
      return Collections.emptyList();
    if (originId.equals(destinationId)) {
      return List.of(graph.getNode(originId));
    }

    Set<String> visited = new HashSet<>();
    Deque<List<Node>> stack = new ArrayDeque<>();

    List<Node> startPath = new ArrayList<>();
    startPath.add(graph.getNode(originId));
    stack.push(startPath);
    visited.add(originId);

    while (!stack.isEmpty()) {
      List<Node> path = stack.pop();
      Node last = path.get(path.size() - 1);

      if (last.getId().equals(destinationId))
        return path;

      for (Node neighbor : graph.getNeighbors(last.getId())) {
        String neighborId = neighbor.getId();
        if (!visited.contains(neighborId)) {
          visited.add(neighborId);
          List<Node> newPath = new ArrayList<>(path);
          newPath.add(neighbor);
          stack.push(newPath);
        }
      }
    }

    return Collections.emptyList();
  }

  /**
   * Cuenta cuantos nodos son alcanzables desde el nodo origen.
   *
   * @param originId identificador del nodo origen
   * @return cantidad de nodos alcanzables (incluyendo el origen)
   */
  public int countReachable(String originId) {
    if (!graph.hasNode(originId))
      return 0;
    Set<String> visited = new HashSet<>();
    dfsRecursive(originId, visited, new ArrayList<>());
    return visited.size();
  }
}
