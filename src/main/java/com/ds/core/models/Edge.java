package com.ds.core.models;

import com.ds.core.enums.RouteStatus;
import java.util.Objects;

/**
 * Representa una arista o conexión entre dos nodos del grafo.
 * <p>
 * Cada arista tiene una distancia (km), un tiempo de recorrido (min)
 * y un estado que puede variar dinámicamente (abierta, congestionada
 * o bloqueada) según las condiciones de tráfico simuladas.
 * </p>
 */
public class Edge {

  private final Node from;
  private final Node to;
  private final double distance;
  private final double time;
  private RouteStatus status;

  /**
   * Construye una arista con estado inicial {@link RouteStatus#OPEN}.
   *
   * @param from     nodo de origen
   * @param to       nodo de destino
   * @param distance distancia en kilómetros (no negativa)
   * @param time     tiempo de recorrido en minutos (no negativa)
   * @throws IllegalArgumentException si algún argumento es inválido
   */
  public Edge(Node from, Node to, double distance, double time) {
    if (from == null)
      throw new IllegalArgumentException("from cannot be null");
    if (to == null)
      throw new IllegalArgumentException("to cannot be null");
    if (distance < 0)
      throw new IllegalArgumentException("distance cannot be negative");
    if (time < 0)
      throw new IllegalArgumentException("time cannot be negative");
    this.from = from;
    this.to = to;
    this.distance = distance;
    this.time = time;
    this.status = RouteStatus.OPEN;
  }

  /**
   * Construye una arista con un estado específico.
   *
   * @param from     nodo de origen
   * @param to       nodo de destino
   * @param distance distancia en kilómetros (no negativa)
   * @param time     tiempo de recorrido en minutos (no negativa)
   * @param status   estado inicial de la ruta
   * @throws IllegalArgumentException si algún argumento es inválido
   */
  public Edge(Node from, Node to, double distance, double time, RouteStatus status) {
    this(from, to, distance, time);
    if (status == null)
      throw new IllegalArgumentException("status cannot be null");
    this.status = status;
  }

  /**
   * @return nodo de origen de la arista
   */
  public Node getFrom() {
    return from;
  }

  /**
   * @return nodo de destino de la arista
   */
  public Node getTo() {
    return to;
  }

  /**
   * @return distancia en kilómetros
   */
  public double getDistance() {
    return distance;
  }

  /**
   * @return tiempo de recorrido en minutos
   */
  public double getTime() {
    return time;
  }

  /**
   * @return estado actual de la ruta (OPEN, CONGESTED, BLOCKED)
   */
  public RouteStatus getStatus() {
    return status;
  }

  /**
   * Actualiza el estado de la ruta. Usado por {@code TrafficSimulator}
   * para reflejar cambios en las condiciones de tráfico.
   *
   * @param status nuevo estado de la ruta
   * @throws IllegalArgumentException si {@code status} es {@code null}
   */
  public void setStatus(RouteStatus status) {
    if (status == null)
      throw new IllegalArgumentException("status cannot be null");
    this.status = status;
  }

  /**
   * @return representación textual de la arista
   */
  @Override
  public String toString() {
    return "Edge{from=" + from.getName() + ", to=" + to.getName() + ", distance=" + distance + ", time=" + time
        + ", status=" + status + "}";
  }

  /**
   * Compara aristas por nodos, distancia y tiempo.
   *
   * @param o objeto a comparar
   * @return {@code true} si todas las propiedades coinciden
   */
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Edge))
      return false;
    Edge edge = (Edge) o;
    return Double.compare(distance, edge.distance) == 0
        && Double.compare(time, edge.time) == 0
        && Objects.equals(from, edge.from)
        && Objects.equals(to, edge.to);
  }

  /**
   * @return hash basado en nodos, distancia y tiempo
   */
  @Override
  public int hashCode() {
    return Objects.hash(from, to, distance, time);
  }
}
