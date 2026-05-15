package com.ds.core.utils;

/**
 * Constantes globales del sistema de gestion de pedidos.
 * <p>
 * Define valores por defecto, umbrales y configuraciones
 * utilizadas transversalmente por los modulos del core.
 * </p>
 */
public final class Constants {

  private Constants() {
  }

  /** Velocidad promedio del repartidor en km/h */
  public static final double DEFAULT_AVERAGE_SPEED_KMH = 30.0;

  /** Tiempo base de preparacion de un pedido en minutos */
  public static final double DEFAULT_PREPARATION_TIME_MIN = 10.0;

  /** Distancia maxima en km para considerar un cliente como cercano */
  public static final double CLOSE_DISTANCE_KM = 3.0;

  /** Distancia en km a partir de la cual se incrementa la prioridad */
  public static final double LONG_DISTANCE_THRESHOLD_KM = 10.0;

  /** Puntos de prioridad adicionales para pedidos de larga distancia */
  public static final int LONG_DISTANCE_PRIORITY_BONUS = 5;

  /** Puntos de prioridad base para pedidos normales */
  public static final int BASE_PRIORITY = 0;

  /** Prioridad maxima permitida */
  public static final int MAX_PRIORITY = 100;

  /** Tiempo maximo estimado de entrega en minutos antes de considerar retraso */
  public static final double MAX_ESTIMATED_TIME_MIN = 120.0;

  /** Factor de conversion de coordenadas a kilometros */
  public static final double DEFAULT_SCALE_FACTOR = 0.1;

  /** Capacidad maxima de pedidos activos en el sistema */
  public static final int MAX_ACTIVE_ORDERS = 50;

  /** Radio de busqueda para clientes cercanos en km */
  public static final double NEARBY_SEARCH_RADIUS_KM = 5.0;
}