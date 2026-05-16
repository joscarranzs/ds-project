package com.ds.core.simulation;

import java.util.List;
import java.util.Random;

import com.ds.core.enums.RouteStatus;
import com.ds.core.graph.Graph;
import com.ds.core.models.Edge;

/// Clase responsable de generar eventos aleatorios que afectan el 
// estado de las rutas en el grafo, como tráfico, accidentes o bloqueos temporales. 
// Estos eventos simulan condiciones dinámicas en la red de rutas, lo que permite,
// por ejemplo, que los algoritmos de búsqueda (Dijkstra, BFS) respondan a cambios en tiempo real.

public class EventGenerator {

    private final Graph graph;
    private final Random random;

    public EventGenerator(Graph graph) {
        this.graph = graph;
        this.random = new Random();
    }
    //Metodo para simular que la ruta esta bloqueada temporalmente
    // lo que afecta la disponibilidad de rutas para los algoritmos de busqueda.

    public void generateRandomEvent() {

        List<Edge> edges = graph.getAllEdges();

        if (edges.isEmpty()) {
            System.out.println("No hay rutas disponibles.");
            return;
        }

        Edge selectedEdge = edges.get(random.nextInt(edges.size()));

        int eventType = random.nextInt(3);

        switch (eventType) {

            // Metodo y caso de tráfico entre nodo A y B
            case 0:

                selectedEdge.setStatus(RouteStatus.CONGESTED);

                System.out.println(
                        "[EVENTO] Tráfico detectado entre "
                                + selectedEdge.getFrom().getName()
                                + " ----> "
                                + selectedEdge.getTo().getName()
                );
                break;

            // Metodo y caso de accidente entre nodo A y B
            case 1:

                selectedEdge.setStatus(RouteStatus.CONGESTED);

                System.out.println(
                        "[EVENTO] Accidente detectado entre "
                                + selectedEdge.getFrom().getName()
                                + " ----> "
                                + selectedEdge.getTo().getName()
                );
                break;

            // Metodo y caso de bloqueo entre nodo A y B
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
            
            //Metodo y caso de reten policial entre nodo A y B
            case 3: 
            selectedEdge.setStatus(RouteStatus.CONGESTED);
            System.out.println(
                "[EVENTO] Retén policial entre "
                        + selectedEdge.getFrom().getName()
                        + " ----> "
                        + selectedEdge.getTo().getName()
            );
        }
    }
}