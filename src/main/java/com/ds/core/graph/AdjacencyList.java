package com.ds.core.graph;

import com.ds.core.models.Edge;
import com.ds.core.models.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementacion interna de lista de adyacencia para el grafo.
 * <p>
 * Almacena para cada nodo (identificado por su id) la lista de aristas
 * que parten desde el. Esta estructura optimiza el acceso a vecinos
 * y reduce el uso de memoria al no almacenar informacion redundante.
 * </p>
 */
public class AdjacencyList {

  private final Map<String, List<Edge>> adjacency;

  /**
   * Construye una lista de adyacencia vacia.
   */
  public AdjacencyList() {
    this.adjacency = new HashMap<>();
  }

  /**
   * Agrega una arista a la lista de adyacencia del nodo origen.
   * Si el nodo origen aun no tiene entrada, se crea una lista nueva.
   *
   * @param edge arista a agregar
   */
  public void addEdge(Edge edge) {
    adjacency.computeIfAbsent(edge.getFrom().getId(), k -> new ArrayList<>()).add(edge);
  }

  /**
   * Retorna la lista de aristas que parten del nodo con el id dado.
   *
   * @param nodeId identificador del nodo origen
   * @return lista de aristas, o lista vacia si el nodo no existe
   */
  public List<Edge> getEdges(String nodeId) {
    return adjacency.getOrDefault(nodeId, Collections.emptyList());
  }

  /**
   * Retorna los nodos vecinos (destino) alcanzables desde el nodo origen.
   *
   * @param nodeId identificador del nodo origen
   * @return lista de nodos destino, o lista vacia si no hay conexiones
   */
  public List<Node> getNeighbors(String nodeId) {
    return adjacency.getOrDefault(nodeId, Collections.emptyList())
        .stream()
        .map(Edge::getTo)
        .collect(Collectors.toList());
  }

  /**
   * Elimina todas las aristas que parten del nodo origen hacia el nodo destino.
   *
   * @param fromId identificador del nodo origen
   * @param toId   identificador del nodo destino
   */
  public void removeEdge(String fromId, String toId) {
    List<Edge> edges = adjacency.get(fromId);
    if (edges != null) {
      edges.removeIf(e -> e.getTo().getId().equals(toId));
    }
  }

  /**
   * Verifica si existe al menos una arista entre dos nodos.
   *
   * @param fromId identificador del nodo origen
   * @param toId   identificador del nodo destino
   * @return {@code true} si existe conexion directa
   */
  public boolean hasEdge(String fromId, String toId) {
    List<Edge> edges = adjacency.get(fromId);
    if (edges == null)
      return false;
    return edges.stream().anyMatch(e -> e.getTo().getId().equals(toId));
  }

  /**
   * Retorna todas las aristas registradas en el grafo.
   *
   * @return lista completa de aristas
   */
  public List<Edge> getAllEdges() {
    return adjacency.values().stream()
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

  /**
   * Elimina todas las referencias al nodo, incluyendo aristas
   * donde aparece como origen y aristas donde aparece como destino.
   *
   * @param nodeId identificador del nodo a eliminar
   */
  public void removeNode(String nodeId) {
    adjacency.remove(nodeId);
    adjacency.values().forEach(edges -> edges.removeIf(e -> e.getTo().getId().equals(nodeId)));
  }

  /**
   * Limpia por completo la lista de adyacencia.
   */
  public void clear() {
    adjacency.clear();
  }

  /**
   * @return cantidad de nodos con aristas registradas
   */
  public int size() {
    return adjacency.size();
  }

  /**
   * @return {@code true} si no hay aristas registradas
   */
  public boolean isEmpty() {
    return adjacency.isEmpty();
  }
}
