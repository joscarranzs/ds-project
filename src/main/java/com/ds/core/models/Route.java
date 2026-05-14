package com.ds.core.models;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representa una ruta calculada entre dos nodos del grafo.
 * <p>
 * La ruta es inmutable: contiene la secuencia de nodos recorridos,
 * la distancia total y el tiempo total estimado. Es el resultado
 * devuelto por los algoritmos de búsqueda ({@code DijkstraAlgorithm},
 * {@code BFSAlgorithm}, etc.).
 * </p>
 */
public class Route {

  private final Node origin;
  private final Node destination;
  private final List<Node> path;
  private final double totalDistance;
  private final double totalTime;

  /**
   * Construye una ruta verificando que todos los parámetros sean válidos.
   * La lista {@code path} se encapsula en una copia no modificable.
   *
   * @param origin        nodo de origen
   * @param destination   nodo de destino
   * @param path          lista ordenada de nodos que conforman la ruta (origen →
   *                      destino)
   * @param totalDistance distancia total en kilómetros
   * @param totalTime     tiempo total estimado en minutos
   * @throws IllegalArgumentException si algún argumento es inválido
   */
  public Route(Node origin, Node destination, List<Node> path, double totalDistance, double totalTime) {
    if (origin == null)
      throw new IllegalArgumentException("origin cannot be null");
    if (destination == null)
      throw new IllegalArgumentException("destination cannot be null");
    if (path == null || path.isEmpty())
      throw new IllegalArgumentException("path cannot be null or empty");
    if (totalDistance < 0)
      throw new IllegalArgumentException("totalDistance cannot be negative");
    if (totalTime < 0)
      throw new IllegalArgumentException("totalTime cannot be negative");
    this.origin = origin;
    this.destination = destination;
    this.path = Collections.unmodifiableList(path);
    this.totalDistance = totalDistance;
    this.totalTime = totalTime;
  }

  /**
   * @return nodo de origen de la ruta
   */
  public Node getOrigin() {
    return origin;
  }

  /**
   * @return nodo de destino de la ruta
   */
  public Node getDestination() {
    return destination;
  }

  /**
   * Devuelve la secuencia completa de nodos desde el origen hasta el destino.
   * La lista retornada es no modificable.
   *
   * @return lista de nodos que componen la ruta
   */
  public List<Node> getPath() {
    return path;
  }

  /**
   * @return distancia total de la ruta en kilómetros
   */
  public double getTotalDistance() {
    return totalDistance;
  }

  /**
   * @return tiempo total estimado de la ruta en minutos
   */
  public double getTotalTime() {
    return totalTime;
  }

  /**
   * @return representación textual de la ruta
   */
  @Override
  public String toString() {
    return "Route{from=" + origin.getName() + ", to=" + destination.getName() + ", distance=" + totalDistance
        + "km, time=" + totalTime + "min}";
  }

  /**
   * Compara rutas por todos sus atributos.
   *
   * @param o objeto a comparar
   * @return {@code true} si todas las propiedades coinciden
   */
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Route))
      return false;
    Route route = (Route) o;
    return Double.compare(totalDistance, route.totalDistance) == 0
        && Double.compare(totalTime, route.totalTime) == 0
        && Objects.equals(origin, route.origin)
        && Objects.equals(destination, route.destination)
        && Objects.equals(path, route.path);
  }

  /**
   * @return hash basado en todos los atributos de la ruta
   */
  @Override
  public int hashCode() {
    return Objects.hash(origin, destination, path, totalDistance, totalTime);
  }
}
