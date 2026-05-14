package com.ds.core.models;

import com.ds.core.enums.NodeType;
import java.util.Objects;

/**
 * Representa un vértice dentro del grafo de rutas.
 * <p>
 * Un nodo puede representar distintos tipos de puntos según el
 * valor de {@link NodeType}: clientes, restaurantes, intersecciones
 * o puntos de entrega.
 * </p>
 */
public class Node {

  private final String id;
  private final String name;
  private final NodeType type;

  /**
   * Construye un nuevo nodo.
   *
   * @param id   identificador único del nodo
   * @param name nombre descriptivo del nodo (ej. "Restaurante Central", "Calle 5
   *             con 10")
   * @param type tipo de nodo según {@link NodeType}
   * @throws IllegalArgumentException si algún argumento es {@code null} o
   *                                  {@code id}/{@code name} están en blanco
   */
  public Node(String id, String name, NodeType type) {
    if (id == null || id.isBlank())
      throw new IllegalArgumentException("id cannot be blank");
    if (name == null || name.isBlank())
      throw new IllegalArgumentException("name cannot be blank");
    if (type == null)
      throw new IllegalArgumentException("type cannot be null");
    this.id = id;
    this.name = name;
    this.type = type;
  }

  /**
   * @return identificador único del nodo
   */
  public String getId() {
    return id;
  }

  /**
   * @return nombre descriptivo del nodo
   */
  public String getName() {
    return name;
  }

  /**
   * @return tipo del nodo (CLIENT, RESTAURANT, INTERSECTION, DELIVERY_POINT)
   */
  public NodeType getType() {
    return type;
  }

  /**
   * @return representación textual del nodo
   */
  @Override
  public String toString() {
    return "Node{id='" + id + "', name='" + name + "', type=" + type + "}";
  }

  /**
   * Compara nodos por su identificador único.
   *
   * @param o objeto a comparar
   * @return {@code true} si ambos nodos tienen el mismo {@code id}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Node))
      return false;
    Node node = (Node) o;
    return Objects.equals(id, node.id);
  }

  /**
   * @return hash basado en el identificador único
   */
  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
