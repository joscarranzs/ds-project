package com.ds.core.simulation;


import com.ds.core.graph.Graph;
import com.ds.core.models.Edge;

import java.util.List;
import java.util.Random;

/**
 * Genera eventos aleatorios dentro del mapa.
 * Estos eventos afectan temporalmente las rutas del grafo.
 */
public class EventGenerator {

    private final Graph graph;
    private final Random random;

    public EventGenerator(Graph graph) {
        this.graph = graph;
        this.random = new Random();
    }

    /**
     * Genera un evento aleatorio sobre una arista random del grafo.
     */
    public void generateRandomEvent() {

        // obtener todas las conexiones del grafo
        List<Edge> edges = graph.getEdges();

        // validar que existan rutas
        if (edges.isEmpty()) {
            System.out.println("No hay rutas disponibles.");
            return;
        }

        // seleccionar una arista aleatoria
        Edge selectedEdge = edges.get(random.nextInt(edges.size()));

        // seleccionar tipo de evento aleatorio
        int eventType = random.nextInt(3);

        switch (eventType) {

            // tráfico
            case 0:
                selectedEdge.setWeight(selectedEdge.getWeight() + 3);

                System.out.println(
                        "[EVENTO] Tráfico detectado entre "
                                + selectedEdge.getOriginId()
                                + " -> "
                                + selectedEdge.getDestinationId()
                                + " | Nuevo peso: "
                                + selectedEdge.getWeight()
                );
                break;

            // accidente
            case 1:
                selectedEdge.setWeight(selectedEdge.getWeight() + 7);

                System.out.println(
                        "[EVENTO] Accidente detectado entre "
                                + selectedEdge.getOriginId()
                                + " -> "
                                + selectedEdge.getDestinationId()
                                + " | Nuevo peso: "
                                + selectedEdge.getWeight()
                );
                break;

            // bloqueo
            case 2:
                selectedEdge.setBlocked(true);

                System.out.println(
                        "[EVENTO] Ruta bloqueada entre "
                                + selectedEdge.getOriginId()
                                + " -> "
                                + selectedEdge.getDestinationId()
                );
                break;
        }
    }
}