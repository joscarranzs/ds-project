package com.ds.core.graph;

import com.ds.core.models.Edge;
import com.ds.core.models.Node;
import java.util.List;

/**
 * Constructor de grafos.
 * <p>
 * Proporciona una interfaz fluida (builder) para construir instancias
 * de {@link Graph} agregando nodos y aristas de forma progresiva.
 * Util para inicializar mapas desde datos de configuracion o entrada.
 * </p>
 */
public class GraphBuilder {

  private final Graph graph;

  /**
   * Construye un builder con un grafo vacio interno.
   */
  public GraphBuilder() {
    this.graph = new Graph();
  }

  /**
   * Agrega un nodo al grafo en construccion.
   *
   * @param node nodo a agregar
   * @return esta instancia del builder (encadenamiento)
   */
  public GraphBuilder addNode(Node node) {
    graph.addNode(node);
    return this;
  }

  /**
   * Agrega una arista al grafo en construccion.
   *
   * @param edge arista a agregar
   * @return esta instancia del builder (encadenamiento)
   */
  public GraphBuilder addEdge(Edge edge) {
    graph.addEdge(edge);
    return this;
  }

  /**
   * Agrega multiples nodos al grafo.
   *
   * @param nodes lista de nodos a agregar
   * @return esta instancia del builder (encadenamiento)
   */
  public GraphBuilder addNodes(List<Node> nodes) {
    if (nodes != null)
      nodes.forEach(graph::addNode);
    return this;
  }

  /**
   * Agrega multiples aristas al grafo.
   *
   * @param edges lista de aristas a agregar
   * @return esta instancia del builder (encadenamiento)
   */
  public GraphBuilder addEdges(List<Edge> edges) {
    if (edges != null)
      edges.forEach(graph::addEdge);
    return this;
  }

  /**
   * Finaliza la construccion y retorna el grafo.
   *
   * @return el grafo construido
   */
  public Graph build() {
    return graph;
  }

  /**
   * Reinicia el builder con un grafo vacio.
   *
   * @return esta instancia del builder (encadenamiento)
   */
  public GraphBuilder reset() {
    graph.clear();
    return this;
  }
}
