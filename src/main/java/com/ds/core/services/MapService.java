package com.ds.core.services;

import java.util.List;

import com.ds.core.datastructure.Graph;
import com.ds.core.engine.RouterCalculator;
import com.ds.core.engine.TimeEstimator;
import com.ds.core.models.Node;
import com.ds.core.models.Order;

/**
 * servicio principal del mapa.
 * conecta el grafo con el motor de rutas y el estimador de tiempo.
 * es el punto de entrada para calcular rutas y tiempos de entrega.
 */
public class MapService {

    // grafo con los nodos y conexiones del mapa
    private final Graph graph;

    // calculadora de rutas (dijkstra)
    private final RouterCalculator routerCalculator;

    // estimador de tiempos de entrega
    private final TimeEstimator timeEstimator;

    /**
     * constructor: recibe el grafo y crea el router y estimador automáticamente.
     *
     * @param graph grafo con todos los nodos y aristas del mapa
     */
    public MapService(Graph graph) {
        this.graph = graph;
        this.routerCalculator = new RouterCalculator(graph);
        this.timeEstimator = new TimeEstimator();
    }

    /**
     * constructor que permite pasar un estimador de tiempo personalizado.
     *
     * @param graph         grafo del mapa
     * @param timeEstimator estimador con velocidad y tiempo de preparación custom
     */
    public MapService(Graph graph, TimeEstimator timeEstimator) {
        this.graph = graph;
        this.routerCalculator = new RouterCalculator(graph);
        this.timeEstimator = timeEstimator;
    }

    /**
     * calcula la ruta más corta entre dos nodos.
     *
     * @param origin      nodo de inicio
     * @param destination nodo de destino
     * @return lista de nodos que forman la ruta
     */
    public List<Node> getRoute(Node origin, Node destination) {
        return routerCalculator.calculateRoute(origin, destination);
    }

        /**
        * calcula la distancia total de una orden.
        * internamente busca la ruta y suma las distancias.
        *
        * @param order orden con nodo de origen y destino
        * @return distancia total en kilómetros
        */

    public double getDistance(Order order) {

    List<Node> route =
        getRoute(order.getOrigin(), order.getDestination());

    return routerCalculator.getTotalDistance(route);
}

    /**
     * calcula el tiempo estimado de entrega para una orden.
     * internamente busca la ruta y estima el tiempo.
     *
     * @param order orden con nodo de origen y destino
     * @return tiempo estimado en minutos
     */
    public double getEstimatedTime(Order order) {
        List<Node> route = getRoute(order.getOrigin(), order.getDestination());
        double distance = routerCalculator.getTotalDistance(route);
        return timeEstimator.estimateTime(route, distance);
    }

    /**
     * retorna un resumen de la entrega: ruta, distancia y tiempo estimado.
     *
     * @param order orden a procesar
     * @return string con el resumen de la entrega
     */
    public String getDeliverySummary(Order order) {
        List<Node> route = getRoute(order.getOrigin(), order.getDestination());

        if (route.isEmpty()) {
            return "no se encontró ruta para la orden #" + order.getId();
        }

        double distance = routerCalculator.getTotalDistance(route);
        double timeMinutes = timeEstimator.estimateTime(route, distance);
        String formattedTime = timeEstimator.formatTime(timeMinutes);

        StringBuilder sb = new StringBuilder();
        sb.append("orden #").append(order.getId()).append("\n");
        sb.append("paradas: ");
        for (int i = 0; i < route.size(); i++) {
            sb.append(route.get(i).getName());
            if (i < route.size() - 1) sb.append(" -> ");
        }
        sb.append("\n");
        sb.append("distancia total: ").append(String.format("%.2f", distance)).append(" km\n");
        sb.append("tiempo estimado: ").append(formattedTime);

        return sb.toString();
    }

    /**
     * verifica si existe una ruta válida entre dos nodos.
     *
     * @param origin      nodo de inicio
     * @param destination nodo de destino
     * @return true si hay ruta, false si no
     */
    public boolean hasRoute(Node origin, Node destination) {
        return !getRoute(origin, destination).isEmpty();
    }

    // getters por si otras clases necesitan acceso directo

    public Graph getGraph() {
        return graph;
    }

    public RouterCalculator getRouterCalculator() {
        return routerCalculator;
    }

    public TimeEstimator getTimeEstimator() {
        return timeEstimator;
    }
}
