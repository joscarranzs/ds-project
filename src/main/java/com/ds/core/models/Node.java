package com.ds.core.models;

import java.util.Objects;

/**
 * Representa un nodo del mapa.
 */
public class Node {

    private final String id;
    private final String name;

    public Node(String id, String name) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El id no puede estar vacío");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof Node)) return false;
        Node node = (Node) o;

        return id.equals(node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

