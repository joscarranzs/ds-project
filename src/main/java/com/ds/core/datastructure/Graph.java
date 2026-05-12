package com.ds.core.datastructure;

import com.ds.core.models.Node;
import com.ds.core.models.Edge;
import java.util.*;

/**
 * Representa un grafo dirigido con nodos y aristas ponderadas.
 */
public class Graph {
    
    private final Map<String, Node> nodes;
    private final Map<String, List<Edge>> adjacencyList;

    public Graph() {
        this.nodes = new HashMap<>();
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Agrega un nodo al grafo.
     * @param node el nodo a agregar
     */
    public void addNode(Node node) {
        if (node != null) {
            nodes.put(node.getId(), node);
            adjacencyList.putIfAbsent(node.getId(), new ArrayList<>());
        }
    }

    /**
     * Agrega una arista dirigida al grafo.
     * @param edge la arista a agregar
     */
    public void addEdge(Edge edge) {
        if (edge != null) {
            String fromId = edge.getFrom().getId();
            addNode(edge.getFrom());
            addNode(edge.getTo());
            adjacencyList.get(fromId).add(edge);
        }
    }

    /**
     * Obtiene todos los nodos del grafo.
     * @return lista de nodos
     */
    public List<Node> getNodes() {
        return new ArrayList<>(nodes.values());
    }

    /**
     * Obtiene un nodo por su ID.
     * @param id el ID del nodo
     * @return el nodo, o null si no existe
     */
    public Node getNodeById(String id) {
        return nodes.get(id);
    }

    /**
     * Obtiene todas las aristas que salen de un nodo.
     * @param nodeId el ID del nodo
     * @return lista de aristas salientes
     */
    public List<Edge> getEdgesFrom(String nodeId) {
        return adjacencyList.getOrDefault(nodeId, new ArrayList<>());
    }

    /**
     * Obtiene el número de nodos en el grafo.
     * @return cantidad de nodos
     */
    public int getNodeCount() {
        return nodes.size();
    }

    /**
     * Obtiene el número total de aristas en el grafo.
     * @return cantidad de aristas
     */
    public int getEdgeCount() {
        return adjacencyList.values().stream().mapToInt(List::size).sum();
    }
}
