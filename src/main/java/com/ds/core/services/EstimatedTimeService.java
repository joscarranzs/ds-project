package com.ds.core.services;

import com.ds.core.algorithms.RouteOptimizer;
import com.ds.core.enums.RouteStatus;
import com.ds.core.graph.GraphManager;
import com.ds.core.models.Order;
import com.ds.core.models.Route;
import com.ds.core.utils.Constants;
import com.ds.core.utils.TimeFormatter;

/**
 * Servicio de estimacion de tiempos de entrega.
 * <p>
 * Calcula el tiempo estimado de entrega para un pedido considerando
 * la distancia de la ruta, el tiempo de preparacion, el estado del
 * trafico en las rutas y la velocidad promedio del repartidor.
 * </p>
 */
public class EstimatedTimeService {

  private final RouteOptimizer optimizer;
  private final GraphManager graphManager;
  private final double averageSpeedKmh;
  private final double basePreparationTime;

  /**
   * Construye el servicio con valores por defecto de {@link Constants}.
   *
   * @param optimizer    optimizador de rutas
   * @param graphManager administrador del grafo
   */
  public EstimatedTimeService(RouteOptimizer optimizer, GraphManager graphManager) {
    this(optimizer, graphManager, Constants.DEFAULT_AVERAGE_SPEED_KMH, Constants.DEFAULT_PREPARATION_TIME_MIN);
  }

  /**
   * Construye el servicio con valores personalizados.
   *
   * @param optimizer           optimizador de rutas
   * @param graphManager        administrador del grafo
   * @param averageSpeedKmh     velocidad promedio en km/h
   * @param basePreparationTime tiempo base de preparacion en minutos
   */
  public EstimatedTimeService(RouteOptimizer optimizer, GraphManager graphManager,
      double averageSpeedKmh, double basePreparationTime) {
    if (optimizer == null)
      throw new IllegalArgumentException("optimizer cannot be null");
    if (graphManager == null)
      throw new IllegalArgumentException("graphManager cannot be null");
    this.optimizer = optimizer;
    this.graphManager = graphManager;
    this.averageSpeedKmh = averageSpeedKmh;
    this.basePreparationTime = basePreparationTime;
  }

  /**
   * Estima el tiempo total de entrega para un pedido.
   * <p>
   * El calculo incluye: tiempo de preparacion + tiempo de viaje
   * (distancia / velocidad) + penalizacion por trafico.
   * </p>
   *
   * @param order pedido a evaluar
   * @return tiempo estimado total en minutos
   */
  public double estimate(Order order) {
    if (order == null)
      throw new IllegalArgumentException("order cannot be null");

    Route route = optimizer.optimizeByTime(
        order.getOrigin().getId(),
        order.getDestination().getId());

    double travelTime;
    if (route != null) {
      travelTime = route.getTotalTime();
    } else {
      double distance = order.getOrigin().getId().equals(order.getDestination().getId())
          ? 0
          : Constants.CLOSE_DISTANCE_KM;
      travelTime = (distance / averageSpeedKmh) * 60;
    }

    double trafficPenalty = calculateTrafficPenalty(order);
    return basePreparationTime + travelTime + trafficPenalty;
  }

  /**
   * Estima el tiempo y lo asigna directamente al pedido.
   *
   * @param order pedido a evaluar
   */
  public void estimateAndAssign(Order order) {
    double estimated = estimate(order);
    order.setEstimatedTime(estimated);
  }

  /**
   * Retorna el tiempo estimado formateado como texto.
   *
   * @param order pedido a evaluar
   * @return cadena formateada (ej. "45min", "1h 30min")
   */
  public String formatEstimatedTime(Order order) {
    return TimeFormatter.format(estimate(order));
  }

  private double calculateTrafficPenalty(Order order) {
    String origin = order.getOrigin().getId();
    String dest = order.getDestination().getId();

    long congestedEdges = graphManager.getGraph().getEdgesFrom(origin).stream()
        .filter(e -> e.getStatus() == RouteStatus.CONGESTED
            || e.getStatus() == RouteStatus.BLOCKED)
        .count();

    return congestedEdges * 5.0;
  }
}
