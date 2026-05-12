package com.ds.core.models;

import java.util.Objects;

/**
 * Representa una conexión entre dos nodos.
 */
public class Edge {

    private final Node from;
    private final Node to;
    private final double distance;

    public Edge(Node from, Node to, double distance) {

        if (from == null) {
            throw new IllegalArgumentException("El nodo origen no puede ser nulo");
        }

        if (to == null) {
            throw new IllegalArgumentException("El nodo destino no puede ser nulo");
        }

        if (distance <= 0) {
            throw new IllegalArgumentException("La distancia debe ser mayor que cero");
        }

        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from=" + from +
                ", to=" + to +
                ", distance=" + distance +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;

        return Double.compare(edge.distance, distance) == 0
                && from.equals(edge.from)
                && to.equals(edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, distance);
    }
}

