package com.ds.core.priority;

import com.ds.core.utils.Constants;

/**
 * Define las reglas y umbrales del sistema de prioridades.
 * <p>
 * Establece como se calculan los bonus por distintos factores
 * (distancia, tiempo de preparacion, trafico) y los umbrales
 * que determinan el nivel de prioridad de cada pedido.
 * </p>
 */
public class PriorityRules {

  private final int maxPriority;
  private final int mediumThreshold;
  private final int highThreshold;
  private final int urgentThreshold;
  private final double longDistanceThreshold;
  private final int longDistanceBonus;

  /**
   * Construye las reglas con valores por defecto tomados de
   * {@link Constants}.
   */
  public PriorityRules() {
    this.maxPriority = Constants.MAX_PRIORITY;
    this.mediumThreshold = 10;
    this.highThreshold = 25;
    this.urgentThreshold = 50;
    this.longDistanceThreshold = Constants.LONG_DISTANCE_THRESHOLD_KM;
    this.longDistanceBonus = Constants.LONG_DISTANCE_PRIORITY_BONUS;
  }

  /**
   * Construye las reglas con valores personalizados.
   *
   * @param maxPriority           prioridad maxima permitida
   * @param mediumThreshold       puntaje minimo para nivel MEDIUM
   * @param highThreshold         puntaje minimo para nivel HIGH
   * @param urgentThreshold       puntaje minimo para nivel URGENT
   * @param longDistanceThreshold distancia en km para considerar larga distancia
   * @param longDistanceBonus     puntos adicionales por larga distancia
   */
  public PriorityRules(int maxPriority, int mediumThreshold, int highThreshold,
      int urgentThreshold, double longDistanceThreshold, int longDistanceBonus) {
    this.maxPriority = maxPriority;
    this.mediumThreshold = mediumThreshold;
    this.highThreshold = highThreshold;
    this.urgentThreshold = urgentThreshold;
    this.longDistanceThreshold = longDistanceThreshold;
    this.longDistanceBonus = longDistanceBonus;
  }

  /**
   * Calcula el bonus por distancia estimada del pedido.
   *
   * @param estimatedTime tiempo estimado de entrega en minutos
   * @return puntos de prioridad adicionales
   */
  public int bonusForDistance(double estimatedTime) {
    if (estimatedTime > 60)
      return longDistanceBonus * 2;
    if (estimatedTime > 30)
      return longDistanceBonus;
    return 0;
  }

  /**
   * Calcula el bonus por tiempo de preparacion.
   *
   * @param preparationTimeMinutes tiempo de preparacion en minutos
   * @return puntos de prioridad adicionales
   */
  public int bonusForPreparationTime(double preparationTimeMinutes) {
    if (preparationTimeMinutes > 20)
      return 0;
    if (preparationTimeMinutes > 10)
      return 5;
    if (preparationTimeMinutes > 0)
      return 10;
    return 0;
  }

  /**
   * Calcula el bonus por la prioridad ya asignada al pedido.
   *
   * @param currentPriority prioridad actual del pedido
   * @return puntos adicionales
   */
  public int bonusForPriority(int currentPriority) {
    if (currentPriority >= urgentThreshold)
      return 10;
    if (currentPriority >= highThreshold)
      return 5;
    return 0;
  }

  /**
   * @return prioridad maxima permitida
   */
  public int getMaxPriority() {
    return maxPriority;
  }

  /**
   * @return puntaje minimo para nivel MEDIUM
   */
  public int getMediumThreshold() {
    return mediumThreshold;
  }

  /**
   * @return puntaje minimo para nivel HIGH
   */
  public int getHighThreshold() {
    return highThreshold;
  }

  /**
   * @return puntaje minimo para nivel URGENT
   */
  public int getUrgentThreshold() {
    return urgentThreshold;
  }

  /**
   * @return distancia en km para considerar larga distancia
   */
  public double getLongDistanceThreshold() {
    return longDistanceThreshold;
  }
}
