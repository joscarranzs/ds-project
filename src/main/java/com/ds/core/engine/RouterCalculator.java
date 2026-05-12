package com.ds.core.engine;

import com.ds.core.datastructure.Graph;
import com.ds.core.models.Node;
import com.ds.core.models.Edge;

import java.util.*;

/**
 * calcula la ruta más corta entre nodos usando el algoritmo de dijkstra.
 * trabaja directamente con el grafo y el heap de órdenes.
 */
public class RouterCalculator {

    // grafo con todos los nodos y conexiones del mapa
    private final Graph graph;

    /**
     * constructor: recibe el grafo que se usará para calcular rutas
     * @param graph grafo cargado con nodos y aristas
     */
    public RouterCalculator(Graph graph) {
        this.graph = graph;
    }

    /**
     * calcula la ruta más corta desde un nodo origen hasta un nodo destino.
     * usa dijkstra simple con mapa de distancias.
     *
     * @param origin    nodo de inicio
     * @param destination nodo de destino
     * @return lista de nodos que forman la ruta, vacía si no existe camino
     */
    public List<Node> calculateRoute(Node origin, Node destination) {
        // mapa de distancias mínimas por nodo
        Map<String, Double> distances = new HashMap<>();
        // mapa para reconstruir el camino (de dónde vine)
        Map<String, String> previous = new HashMap<>();
        // cola de prioridad: [distancia, nodoId]
        PriorityQueue<Object[]> queue = new PriorityQueue<>(Comparator.comparingDouble(a -> (double) a[0]));

        // inicializar distancias en infinito
        for (Node node : graph.getNodes()) {
            distances.put(node.getId(), Double.MAX_VALUE);
        }

        // el origen tiene distancia 0
        distances.put(origin.getId(), 0.0);
        queue.offer(new Object[]{0.0, origin.getId()});

        while (!queue.isEmpty()) {
            Object[] current = queue.poll();
            String currentId = (String) current[1];
            double currentDist = (double) current[0];

            // si ya procesamos este nodo con menor distancia, ignorar
            if (currentDist > distances.get(currentId)) continue;

            // si llegamos al destino, parar
            if (currentId.equals(destination.getId())) break;

            // revisar vecinos
            for (Edge edge : graph.getEdgesFrom(currentId)) {
                String neighborId = edge.getTo().getId();
                double newDist = currentDist + edge.getDistance();

                if (newDist < distances.getOrDefault(neighborId, Double.MAX_VALUE)) {
                    distances.put(neighborId, newDist);
                    previous.put(neighborId, currentId);
                    queue.offer(new Object[]{newDist, neighborId});
                }
            }
        }

        // reconstruir la ruta desde el destino hacia el origen
        return buildPath(previous, origin, destination);
    }

    /**
     * reconstruye la lista de nodos del camino a partir del mapa de previos.
     *
     * @param previous     mapa nodo -> nodo anterior
     * @param origin       nodo de inicio
     * @param destination  nodo de llegada
     * @return lista de nodos en orden de recorrido
     */
    private List<Node> buildPath(Map<String, String> previous, Node origin, Node destination) {
        List<Node> path = new ArrayList<>();
        String step = destination.getId();

        // si el destino no fue alcanzado, regresar lista vacía
        if (!previous.containsKey(step) && !step.equals(origin.getId())) {
            return path;
        }

        // recorrer hacia atrás desde el destino
        while (!step.equals(origin.getId())) {
            path.add(0, graph.getNodeById(step));
            step = previous.get(step);
        }
        path.add(0, origin);

        return path;
    }

    /**
     * calcula la distancia total de una lista de nodos (ruta).
     *
     * @param route lista de nodos de la ruta
     * @return distancia total acumulada
     */
    public double getTotalDistance(List<Node> route) {
        double total = 0.0;
        for (int i = 0; i < route.size() - 1; i++) {
            String from = route.get(i).getId();
            String to = route.get(i + 1).getId();
            for (Edge edge : graph.getEdgesFrom(from)) {
                if (edge.getTo().getId().equals(to)) {
                    total += edge.getDistance();
                    break;
                }
            }
        }
        return total;
    }
}