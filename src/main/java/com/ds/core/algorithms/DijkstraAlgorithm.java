package com.ds.core.algorithms;

import com.ds.core.graph.Graph;
import com.ds.core.models.Edge;
import com.ds.core.models.Node;
import com.ds.core.models.Route;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Implementacion del algoritmo de Dijkstra para encontrar la ruta
 * mas corta o rapida entre dos nodos en un grafo ponderado.
 * <p>
 * Soporta dos modos de optimizacion: por distancia (km) o por
 * tiempo (minutos), utilizando el peso correspondiente de cada arista.
 * </p>
 */
public class DijkstraAlgorithm {

  private final Graph graph;

  /**
   * Construye el algoritmo asociado a un grafo.
   *
   * @param graph grafo sobre el cual operar
   */
  public DijkstraAlgorithm(Graph graph) {
    if (graph == null)
      throw new IllegalArgumentException("graph cannot be null");
    this.graph = graph;
  }

  /**
   * Calcula la ruta mas corta entre dos nodos usando la distancia
   * de las aristas como peso.
   *
   * @param originId      identificador del nodo origen
   * @param destinationId identificador del nodo destino
   * @return ruta optima, o {@code null} si no existe camino
   */
  public Route findShortestPath(String originId, String destinationId) {
    return findPath(originId, destinationId, true);
  }

  /**
   * Calcula la ruta mas rapida entre dos nodos usando el tiempo
   * de las aristas como peso.
   *
   * @param originId      identificador del nodo origen
   * @param destinationId identificador del nodo destino
   * @return ruta optima, o {@code null} si no existe camino
   */
  public Route findFastestPath(String originId, String destinationId) {
    return findPath(originId, destinationId, false);
  }

  private Route findPath(String originId, String destinationId, boolean useDistance) {
    if (!graph.hasNode(originId))
      return null;
    if (!graph.hasNode(destinationId))
      return null;

    Node origin = graph.getNode(originId);
    Node destination = graph.getNode(destinationId);

    Map<String, Double> distances = new HashMap<>();
    Map<String, String> previous = new HashMap<>();
    PriorityQueue<NodeDistance> queue = new PriorityQueue<>();

    for (Node node : graph.getNodes()) {
      distances.put(node.getId(), Double.MAX_VALUE);
    }
    distances.put(originId, 0.0);
    queue.add(new NodeDistance(originId, 0.0));

    while (!queue.isEmpty()) {
      NodeDistance current = queue.poll();
      String currentId = current.nodeId;

      if (currentId.equals(destinationId))
        break;
      if (current.distance > distances.get(currentId))
        continue;

      for (Edge edge : graph.getEdgesFrom(currentId)) {
        String neighborId = edge.getTo().getId();
        double weight = useDistance ? edge.getDistance() : edge.getTime();
        double newDist = distances.get(currentId) + weight;

        if (newDist < distances.get(neighborId)) {
          distances.put(neighborId, newDist);
          previous.put(neighborId, currentId);
          queue.add(new NodeDistance(neighborId, newDist));
        }
      }
    }

    if (!previous.containsKey(destinationId) && !originId.equals(destinationId))
      return null;

    List<Node> path = buildPath(previous, origin, destination);
    double totalDistance = 0;
    double totalTime = 0;
    for (int i = 0; i < path.size() - 1; i++) {
      String from = path.get(i).getId();
      String to = path.get(i + 1).getId();
      for (Edge edge : graph.getEdgesFrom(from)) {
        if (edge.getTo().getId().equals(to)) {
          totalDistance += edge.getDistance();
          totalTime += edge.getTime();
          break;
        }
      }
    }

    return new Route(origin, destination, path, totalDistance, totalTime);
  }

  private List<Node> buildPath(Map<String, String> previous, Node origin, Node destination) {
    List<Node> path = new ArrayList<>();
    String current = destination.getId();
    while (current != null) {
      path.add(graph.getNode(current));
      current = previous.get(current);
    }
    Collections.reverse(path);

    if (!path.get(0).equals(origin)) {
      path.add(0, origin);
    }
    return path;
  }

  private static class NodeDistance implements Comparable<NodeDistance> {
    final String nodeId;
    final double distance;

    NodeDistance(String nodeId, double distance) {
      this.nodeId = nodeId;
      this.distance = distance;
    }

    @Override
    public int compareTo(NodeDistance o) {
      return Double.compare(this.distance, o.distance);
    }
  }
}
