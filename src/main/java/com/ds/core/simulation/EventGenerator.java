package com.ds.core.simulation;

import java.util.List;
import java.util.Random;

import com.ds.core.enums.RouteStatus;
import com.ds.core.graph.Graph;
import com.ds.core.models.Edge;

/**
 * Genera eventos aleatorios que afectan dinamicamente el estado de las rutas
 * en el grafo, como trafico, accidentes, bloqueos temporales o retenes
 * policiales. Esto permite que los algoritmos de busqueda respondan a cambios
 * en tiempo real.
 */
public class EventGenerator {

  private final Graph graph;
  private final Random random;

  /**
   * Construye un generador de eventos asociado al grafo dado.
   *
   * @param graph grafo sobre el cual se generaran los eventos
   */
  public EventGenerator(Graph graph) {
    this.graph = graph;
    this.random = new Random();
  }

  /**
   * Selecciona una arista al azar y le aplica un evento aleatorio:
   * <ul>
   *   <li>0 — trafico (CONGESTED)</li>
   *   <li>1 — accidente (BLOCKED)</li>
   *   <li>2 — bloqueo (BLOCKED + blocked flag)</li>
   *   <li>3 — reten policial (CONGESTED)</li>
   * </ul>
   */
  public void generateRandomEvent() {
    List<Edge> edges = graph.getAllEdges();

    if (edges.isEmpty()) {
      System.out.println("No hay rutas disponibles.");
      return;
    }

    Edge selectedEdge = edges.get(random.nextInt(edges.size()));
    int eventType = random.nextInt(4);

    switch (eventType) {
      case 0:
        selectedEdge.setStatus(RouteStatus.CONGESTED);
        System.out.println(
            "[EVENTO] Trafico detectado entre "
                + selectedEdge.getFrom().getName()
                + " ----> "
                + selectedEdge.getTo().getName()
        );
        break;

      case 1:
        selectedEdge.setStatus(RouteStatus.BLOCKED);
        System.out.println(
            "[EVENTO] Accidente detectado entre "
                + selectedEdge.getFrom().getName()
                + " ----> "
                + selectedEdge.getTo().getName()
        );
        break;

      case 2:
        selectedEdge.setBlocked(true);
        selectedEdge.setStatus(RouteStatus.BLOCKED);
        System.out.println(
            "[EVENTO] Ruta bloqueada entre "
                + selectedEdge.getFrom().getName()
                + " ----> "
                + selectedEdge.getTo().getName()
        );
        break;

      case 3:
        selectedEdge.setStatus(RouteStatus.CONGESTED);
        System.out.println(
            "[EVENTO] Reten policial entre "
                + selectedEdge.getFrom().getName()
                + " ----> "
                + selectedEdge.getTo().getName()
        );
        break;
    }
  }
}