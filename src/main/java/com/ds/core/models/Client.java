package com.ds.core.models;

import java.util.Objects;

/**
 * Representa un cliente dentro del sistema.
 * <p>
 * Almacena la información de contacto del cliente y su ubicación
 * representada como un {@link Node} en el grafo de rutas.
 * </p>
 */
public class Client {

  private final String id;
  private final String name;
  private final String phone;
  private final String address;
  private final Node location;

  /**
   * Construye un nuevo cliente.
   *
   * @param id       identificador único del cliente
   * @param name     nombre completo del cliente
   * @param phone    número de teléfono de contacto
   * @param address  dirección física del cliente
   * @param location nodo del grafo que representa la ubicación del cliente
   * @throws IllegalArgumentException si algún argumento es {@code null} o está en
   *                                  blanco
   */
  public Client(String id, String name, String phone, String address, Node location) {
    if (id == null || id.isBlank())
      throw new IllegalArgumentException("id cannot be blank");
    if (name == null || name.isBlank())
      throw new IllegalArgumentException("name cannot be blank");
    if (phone == null || phone.isBlank())
      throw new IllegalArgumentException("phone cannot be blank");
    if (address == null || address.isBlank())
      throw new IllegalArgumentException("address cannot be blank");
    if (location == null)
      throw new IllegalArgumentException("location cannot be null");
    this.id = id;
    this.name = name;
    this.phone = phone;
    this.address = address;
    this.location = location;
  }

  /**
   * @return identificador único del cliente
   */
  public String getId() {
    return id;
  }

  /**
   * @return nombre del cliente
   */
  public String getName() {
    return name;
  }

  /**
   * @return número de teléfono de contacto
   */
  public String getPhone() {
    return phone;
  }

  /**
   * @return dirección física del cliente
   */
  public String getAddress() {
    return address;
  }

  /**
   * @return nodo del grafo correspondiente a la ubicación del cliente
   */
  public Node getLocation() {
    return location;
  }

  /**
   * @return representación textual del cliente
   */
  @Override
  public String toString() {
    return "Client{id='" + id + "', name='" + name + "', phone='" + phone + "'}";
  }

  /**
   * Compara clientes por su identificador único.
   *
   * @param o objeto a comparar
   * @return {@code true} si ambos clientes tienen el mismo {@code id}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Client))
      return false;
    Client client = (Client) o;
    return Objects.equals(id, client.id);
  }

  /**
   * @return hash basado en el identificador único
   */
  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
