package com.ds.core.algorithms;

import com.ds.core.graph.Graph;
import com.ds.core.models.Node;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Implementacion del algoritmo BFS (Breadth First Search) para
 * recorrido por amplitud sobre el grafo.
 * <p>
 * Util para buscar clientes cercanos, explorar zonas y encontrar
 * la ruta con menor cantidad de aristas (independientemente del peso).
 * </p>
 */
public class BFSAlgorithm {

  private final Graph graph;

  /**
   * Construye el algoritmo asociado a un grafo.
   *
   * @param graph grafo sobre el cual operar
   */
  public BFSAlgorithm(Graph graph) {
    if (graph == null) throw new IllegalArgumentException("graph cannot be null");
    this.graph = graph;
  }

  /**
   * Busca todos los nodos alcanzables desde el nodo origen
   * hasta una profundidad maxima (numero de aristas).
   *
   * @param originId    identificador del nodo origen
   * @param maxDepth    profundidad maxima de busqueda
   * @return lista de nodos encontrados dentro del radio
   */
  public List<Node> findNodesWithinDepth(String originId, int maxDepth) {
    if (!graph.hasNode(originId)) return Collections.emptyList();
    if (maxDepth < 0) return Collections.emptyList();

    Set<String> visited = new HashSet<>();
    List<Node> result = new ArrayList<>();
    Queue<String> queue = new ArrayDeque<>();
    Map<String, Integer> depth = new HashMap<>();

    queue.add(originId);
    visited.add(originId);
    depth.put(originId, 0);

    while (!queue.isEmpty()) {
      String current = queue.poll();
      int currentDepth = depth.get(current);

      if (currentDepth > 0) {
        result.add(graph.getNode(current));
      }

      if (currentDepth >= maxDepth) continue;

      for (Node neighbor : graph.getNeighbors(current)) {
        String neighborId = neighbor.getId();
        if (!visited.contains(neighborId)) {
          visited.add(neighborId);
          depth.put(neighborId, currentDepth + 1);
          queue.add(neighborId);
        }
      }
    }

    return result;
  }

  /**
   * Verifica si existe un camino entre dos nodos usando BFS.
   *
   * @param originId      identificador del nodo origen
   * @param destinationId identificador del nodo destino
   * @return {@code true} si existe un camino
   */
  public boolean hasPath(String originId, String destinationId) {
    if (!graph.hasNode(originId) || !graph.hasNode(destinationId)) return false;
    if (originId.equals(destinationId)) return true;

    Set<String> visited = new HashSet<>();
    Queue<String> queue = new ArrayDeque<>();
    queue.add(originId);
    visited.add(originId);

    while (!queue.isEmpty()) {
      String current = queue.poll();
      for (Node neighbor : graph.getNeighbors(current)) {
        String neighborId = neighbor.getId();
        if (neighborId.equals(destinationId)) return true;
        if (!visited.contains(neighborId)) {
          visited.add(neighborId);
          queue.add(neighborId);
        }
      }
    }

    return false;
  }

  /**
   * Encuentra la ruta con la menor cantidad de aristas entre dos nodos.
   *
   * @param originId      identificador del nodo origen
   * @param destinationId identificador del nodo destino
   * @return lista ordenada de nodos desde origen a destino, o lista vacia
   */
  public List<Node> findShortestPathByEdges(String originId, String destinationId) {
    if (!graph.hasNode(originId) || !graph.hasNode(destinationId)) return Collections.emptyList();

    Set<String> visited = new HashSet<>();
    Queue<List<Node>> queue = new ArrayDeque<>();

    List<Node> startPath = new ArrayList<>();
    startPath.add(graph.getNode(originId));
    queue.add(startPath);
    visited.add(originId);

    while (!queue.isEmpty()) {
      List<Node> path = queue.poll();
      Node last = path.get(path.size() - 1);

      if (last.getId().equals(destinationId)) return path;

      for (Node neighbor : graph.getNeighbors(last.getId())) {
        String neighborId = neighbor.getId();
        if (!visited.contains(neighborId)) {
          visited.add(neighborId);
          List<Node> newPath = new ArrayList<>(path);
          newPath.add(neighbor);
          queue.add(newPath);
        }
      }
    }

    return Collections.emptyList();
  }
}