package com.ds.core.algorithms;

import com.ds.core.graph.Graph;
import com.ds.core.models.Route;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Coordinador de algoritmos de busqueda.
 * <p>
 * Selecciona la mejor ruta disponible segun el criterio deseado
 * (distancia minima, tiempo minimo, menor cantidad de aristas)
 * utilizando los algoritmos implementados.
 * </p>
 */
public class RouteOptimizer {

  private final DijkstraAlgorithm dijkstra;
  private final BFSAlgorithm bfs;
  private final DFSAlgorithm dfs;

  /**
   * Construye el optimizador asociado a un grafo.
   *
   * @param graph grafo sobre el cual operar
   */
  public RouteOptimizer(Graph graph) {
    if (graph == null)
      throw new IllegalArgumentException("graph cannot be null");
    this.dijkstra = new DijkstraAlgorithm(graph);
    this.bfs = new BFSAlgorithm(graph);
    this.dfs = new DFSAlgorithm(graph);
  }

  /**
   * Obtiene la ruta mas corta en distancia usando Dijkstra.
   *
   * @param originId      identificador del nodo origen
   * @param destinationId identificador del nodo destino
   * @return ruta optima por distancia, o {@code null}
   */
  public Route optimizeByDistance(String originId, String destinationId) {
    return dijkstra.findShortestPath(originId, destinationId);
  }

  /**
   * Obtiene la ruta mas rapida en tiempo usando Dijkstra.
   *
   * @param originId      identificador del nodo origen
   * @param destinationId identificador del nodo destino
   * @return ruta optima por tiempo, o {@code null}
   */
  public Route optimizeByTime(String originId, String destinationId) {
    return dijkstra.findFastestPath(originId, destinationId);
  }

  /**
   * Verifica si existe conexion entre dos nodos usando BFS.
   *
   * @param originId      identificador del nodo origen
   * @param destinationId identificador del nodo destino
   * @return {@code true} si hay al menos un camino
   */
  public boolean hasConnection(String originId, String destinationId) {
    return bfs.hasPath(originId, destinationId);
  }

  /**
   * Encuentra rutas alternativas usando todos los algoritmos
   * y retorna la mejor segun el criterio especificado.
   *
   * @param originId      identificador del nodo origen
   * @param destinationId identificador del nodo destino
   * @param criterion     criterio de optimizacion
   * @return mejor ruta segun el criterio, o {@code null}
   */
  public Route optimize(String originId, String destinationId, OptimizationCriterion criterion) {
    switch (criterion) {
      case DISTANCE:
        return dijkstra.findShortestPath(originId, destinationId);
      case TIME:
        return dijkstra.findFastestPath(originId, destinationId);
      case EDGES:
        var path = bfs.findShortestPathByEdges(originId, destinationId);
        if (path.isEmpty())
          return null;
        double dist = 0;
        double time = 0;
        return new Route(path.get(0), path.get(path.size() - 1), path, dist, time);
      default:
        return dijkstra.findShortestPath(originId, destinationId);
    }
  }

  /**
   * Calcula todas las rutas posibles entre dos nodos usando los
   * distintos algoritmos y retorna la mejor en cada categoria.
   *
   * @param originId      identificador del nodo origen
   * @param destinationId identificador del nodo destino
   * @return lista de rutas optimizadas, una por criterio
   */
  public List<Route> getAllOptimizations(String originId, String destinationId) {
    return List.of(
        optimize(originId, destinationId, OptimizationCriterion.DISTANCE),
        optimize(originId, destinationId, OptimizationCriterion.TIME),
        optimize(originId, destinationId, OptimizationCriterion.EDGES)).stream()
        .filter(r -> r != null)
        .collect(Collectors.toList());
  }

  /**
   * Obtiene el algoritmo Dijkstra para uso directo.
   *
   * @return instancia de DijkstraAlgorithm
   */
  public DijkstraAlgorithm getDijkstra() {
    return dijkstra;
  }

  /**
   * Obtiene el algoritmo BFS para uso directo.
   *
   * @return instancia de BFSAlgorithm
   */
  public BFSAlgorithm getBfs() {
    return bfs;
  }

  /**
   * Obtiene el algoritmo DFS para uso directo.
   *
   * @return instancia de DFSAlgorithm
   */
  public DFSAlgorithm getDfs() {
    return dfs;
  }

  /**
   * Criterios de optimizacion disponibles.
   */
  public enum OptimizationCriterion {
    /** Minimizar distancia total */
    DISTANCE,
    /** Minimizar tiempo total */
    TIME,
    /** Minimizar cantidad de aristas */
    EDGES
  }
}
