package com.ds.core.graph;

import com.ds.core.models.Edge;
import com.ds.core.models.Node;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase principal del grafo de rutas.
 * <p>
 * Administra el conjunto de nodos y sus conexiones mediante una
 * {@link AdjacencyList}. Permite agregar, consultar y eliminar
 * nodos y aristas, y sirve como base para los algoritmos de
 * busqueda ({@code DijkstraAlgorithm}, {@code BFSAlgorithm}, etc.).
 * </p>
 */
public class Graph {

  private final Map<String, Node> nodes;
  private final AdjacencyList adjacencyList;

  /**
   * Construye un grafo vacio.
   */
  public Graph() {
    this.nodes = new HashMap<>();
    this.adjacencyList = new AdjacencyList();
  }

  /**
   * Agrega un nodo al grafo. Si el nodo ya existe (mismo id),
   * no se realiza ninguna accion.
   *
   * @param node nodo a agregar
   * @throws IllegalArgumentException si {@code node} es {@code null}
   */
  public void addNode(Node node) {
    if (node == null)
      throw new IllegalArgumentException("node cannot be null");
    nodes.putIfAbsent(node.getId(), node);
  }

  /**
   * Agrega una arista al grafo. Los nodos origen y destino deben
   * existir previamente; de lo contrario se registran automaticamente.
   *
   * @param edge arista a agregar
   * @throws IllegalArgumentException si {@code edge} es {@code null}
   */
  public void addEdge(Edge edge) {
    if (edge == null)
      throw new IllegalArgumentException("edge cannot be null");
    addNode(edge.getFrom());
    addNode(edge.getTo());
    adjacencyList.addEdge(edge);
  }

  /**
   * Busca un nodo por su identificador.
   *
   * @param id identificador del nodo
   * @return el nodo encontrado, o {@code null} si no existe
   */
  public Node getNode(String id) {
    return nodes.get(id);
  }

  /**
   * Retorna todos los nodos del grafo.
   *
   * @return coleccion no modificable de nodos
   */
  public Collection<Node> getNodes() {
    return Collections.unmodifiableCollection(nodes.values());
  }

  /**
   * Retorna la lista de aristas que parten del nodo con el id dado.
   *
   * @param nodeId identificador del nodo origen
   * @return lista de aristas, o lista vacia si el nodo no existe
   */
  public List<Edge> getEdgesFrom(String nodeId) {
    return adjacencyList.getEdges(nodeId);
  }

  /**
   * Retorna todos los vecinos alcanzables desde el nodo origen.
   *
   * @param nodeId identificador del nodo origen
   * @return lista de nodos destino
   */
  public List<Node> getNeighbors(String nodeId) {
    return adjacencyList.getNeighbors(nodeId);
  }

  /**
   * Verifica si un nodo existe en el grafo.
   *
   * @param id identificador del nodo
   * @return {@code true} si el nodo esta registrado
   */
  public boolean hasNode(String id) {
    return nodes.containsKey(id);
  }

  /**
   * Verifica si existe conexion directa entre dos nodos.
   *
   * @param fromId identificador del nodo origen
   * @param toId   identificador del nodo destino
   * @return {@code true} si existe al menos una arista entre ambos
   */
  public boolean hasEdge(String fromId, String toId) {
    return adjacencyList.hasEdge(fromId, toId);
  }

  /**
   * Elimina un nodo y todas sus conexiones del grafo.
   *
   * @param nodeId identificador del nodo a eliminar
   */
  public void removeNode(String nodeId) {
    nodes.remove(nodeId);
    adjacencyList.removeNode(nodeId);
  }

  /**
   * Elimina las aristas entre dos nodos.
   *
   * @param fromId identificador del nodo origen
   * @param toId   identificador del nodo destino
   */
  public void removeEdge(String fromId, String toId) {
    adjacencyList.removeEdge(fromId, toId);
  }

  /**
   * Retorna todas las aristas registradas en el grafo.
   *
   * @return lista completa de aristas
   */
  public List<Edge> getAllEdges() {
    return adjacencyList.getAllEdges();
  }

  /**
   * @return cantidad de nodos en el grafo
   */
  public int getNodeCount() {
    return nodes.size();
  }

  /**
   * @return cantidad de aristas registradas
   */
  public int getEdgeCount() {
    return adjacencyList.getAllEdges().size();
  }

  /**
   * @return {@code true} si el grafo no tiene nodos
   */
  public boolean isEmpty() {
    return nodes.isEmpty();
  }

  /**
   * Limpia el grafo por completo.
   */
  public void clear() {
    nodes.clear();
    adjacencyList.clear();
  }
}
