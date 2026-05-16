package com.ds.core.simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.ds.core.enums.PriorityLevel;
import com.ds.core.enums.TrafficCondition;
import com.ds.core.models.Edge;

/**
 * Simula condiciones de trafico que afectan los tiempos de las rutas.
 * <p>
 * Asigna una condicion (FREE, MODERATE, HEAVY, BLOCKED) a cada arista
 * del grafo y proporciona factores de multiplicacion sobre el tiempo
 * base de cada una. Es usado por {@code DeliverySimulator},
 * {@code GraphManager} y {@code EstimatedTimeService}.
 * </p>
 */
public class TrafficSimulator {

  private static final Map<TrafficCondition, Double> FACTORS = new HashMap<>();

  static {
    FACTORS.put(TrafficCondition.FREE, 1.0);
    FACTORS.put(TrafficCondition.MODERATE, 1.4);
    FACTORS.put(TrafficCondition.HEAVY, 2.0);
    FACTORS.put(TrafficCondition.BLOCKED, Double.MAX_VALUE);
  }

  private final Random random = new Random();
  private final Map<String, TrafficCondition> edgeConditions = new HashMap<>();

  /**
   * Genera una condicion de trafico aleatoria segun la prioridad del pedido.
   * <p>
   * Los pedidos {@code URGENT} nunca reciben BLOCKED y tienen mayor
   * probabilidad de FREE.
   * </p>
   *
   * @param priority prioridad del pedido
   * @return condicion de trafico generada
   */
  public TrafficCondition generateCondition(PriorityLevel priority) {
    int roll = random.nextInt(100);

    if (priority == PriorityLevel.URGENT) {
      if (roll < 60) return TrafficCondition.FREE;
      if (roll < 90) return TrafficCondition.MODERATE;
      return TrafficCondition.HEAVY;
    }

    if (roll < 40) return TrafficCondition.FREE;
    if (roll < 75) return TrafficCondition.MODERATE;
    if (roll < 95) return TrafficCondition.HEAVY;
    return TrafficCondition.BLOCKED;
  }

  /**
   * Asigna una condicion de trafico aleatoria a cada arista de la lista.
   *
   * @param edges lista de aristas a simular
   */
  public void simulateEdges(List<Edge> edges) {
    for (Edge edge : edges) {
      TrafficCondition condition = generateCondition(PriorityLevel.MEDIUM);
      edgeConditions.put(edgeKey(edge), condition);
    }
  }

  /**
   * Devuelve el tiempo ajustado de una arista segun su condicion de trafico.
   *
   * @param edge arista a evaluar
   * @return tiempo base multiplicado por el factor de trafico
   */
  public double getAdjustedWeight(Edge edge) {
    TrafficCondition condition = edgeConditions.getOrDefault(edgeKey(edge), TrafficCondition.FREE);
    return edge.getTime() * FACTORS.get(condition);
  }

  /**
   * Calcula el tiempo total ajustado de una lista de aristas segun la
   * prioridad del pedido.
   *
   * @param edges    lista de aristas que componen la ruta
   * @param priority prioridad del pedido
   * @return tiempo total ajustado, o {@code Double.MAX_VALUE} si alguna
   *         arista esta bloqueada
   */
  public double calculateAdjustedTime(List<Edge> edges, PriorityLevel priority) {
    if (edges == null || edges.isEmpty()) return 0.0;

    double totalTime = 0.0;

    for (Edge edge : edges) {
      TrafficCondition condition = generateCondition(priority);
      edgeConditions.put(edgeKey(edge), condition);

      if (condition == TrafficCondition.BLOCKED) {
        return Double.MAX_VALUE;
      }

      totalTime += edge.getTime() * FACTORS.get(condition);
    }

    return totalTime;
  }

  /**
   * Consulta la condicion actual de una arista.
   *
   * @param edge arista a consultar
   * @return condicion de trafico, o FREE si no se ha simulado
   */
  public TrafficCondition getCondition(Edge edge) {
    return edgeConditions.getOrDefault(edgeKey(edge), TrafficCondition.FREE);
  }

  /**
   * Limpia todas las condiciones simuladas.
   */
  public void reset() {
    edgeConditions.clear();
  }

  /**
   * Genera una clave unica compuesta para identificar una arista en el mapa
   * de condiciones, usando los IDs de los nodos origen y destino.
   *
   * @param edge arista a identificar
   * @return clave en formato "fromId->toId"
   */
  private String edgeKey(Edge edge) {
    return edge.getFrom().getId() + "->" + edge.getTo().getId();
  }
}