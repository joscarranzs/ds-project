package com.ds.core.priority;

import com.ds.core.enums.PriorityLevel;
import com.ds.core.models.Order;

/**
 * Calcula la prioridad de un pedido basandose en factores
 * como la distancia, el tiempo de preparacion y el trafico.
 * <p>
 * Convierte el puntaje numerico en un {@link PriorityLevel}
 * para facilitar la clasificacion y visualizacion.
 * </p>
 */
public class PriorityCalculator {

  private final PriorityRules rules;

  /**
   * Construye el calculador con las reglas de prioridad definidas.
   *
   * @param rules reglas de prioridad del sistema
   */
  public PriorityCalculator(PriorityRules rules) {
    if (rules == null) throw new IllegalArgumentException("rules cannot be null");
    this.rules = rules;
  }

  /**
   * Calcula el puntaje de prioridad para un pedido.
   * <p>
   * El puntaje se obtiene sumando los bonus definidos en las reglas
   * segun la distancia y el tiempo de preparacion del pedido.
   * </p>
   *
   * @param order pedido a evaluar
   * @return puntaje de prioridad (entre 0 y {@code MAX_PRIORITY})
   */
  public int calculate(Order order) {
    if (order == null) throw new IllegalArgumentException("order cannot be null");

    int score = 0;
    score += rules.bonusForDistance(order.getEstimatedTime());
    score += rules.bonusForPreparationTime(0);
    score += rules.bonusForPriority(order.getPriority());

    return Math.min(score, rules.getMaxPriority());
  }

  /**
   * Calcula la prioridad y la asigna directamente al pedido.
   *
   * @param order pedido a evaluar y actualizar
   * @return el nivel de prioridad asignado
   */
  public PriorityLevel calculateAndAssign(Order order) {
    int score = calculate(order);
    order.setPriority(score);
    return toLevel(score);
  }

  /**
   * Convierte un puntaje numerico a un nivel de prioridad.
   *
   * @param score puntaje de prioridad
   * @return nivel correspondiente
   */
  public PriorityLevel toLevel(int score) {
    if (score >= rules.getUrgentThreshold()) return PriorityLevel.URGENT;
    if (score >= rules.getHighThreshold()) return PriorityLevel.HIGH;
    if (score >= rules.getMediumThreshold()) return PriorityLevel.MEDIUM;
    return PriorityLevel.LOW;
  }
}