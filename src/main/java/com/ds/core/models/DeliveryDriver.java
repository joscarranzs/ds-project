package com.ds.core.models;

import java.util.Objects;

/**
 * Representa un repartidor dentro del sistema de logistica.
 * <p>
 * Cada repartidor tiene un nombre, una ubicacion actual representada
 * como un {@link Node}, un estado definido por {@link DriverStatus}
 * y puede tener un pedido asignado. La disponibilidad se determina
 * tanto por el estado como por la ausencia de un pedido activo.
 * </p>
 */
public class DeliveryDriver {

  private final String name;
  private Node currentLocation;
  private DriverStatus status;
  private Order assignedOrder;

  /**
   * Construye un repartidor con estado inicial {@link DriverStatus#AVAILABLE}.
   *
   * @param name            nombre del repartidor
   * @param currentLocation ubicacion inicial del repartidor
   * @throws IllegalArgumentException si algun argumento es {@code null} o
   *                                  {@code name} esta en blanco
   */
  public DeliveryDriver(String name, Node currentLocation) {
    if (name == null || name.isBlank())
      throw new IllegalArgumentException("name cannot be blank");
    if (currentLocation == null)
      throw new IllegalArgumentException("currentLocation cannot be null");
    this.name = name;
    this.currentLocation = currentLocation;
    this.status = DriverStatus.AVAILABLE;
  }

  /**
   * @return nombre del repartidor
   */
  public String getName() {
    return name;
  }

  /**
   * @return ubicacion actual del repartidor
   */
  public Node getCurrentLocation() {
    return currentLocation;
  }

  /**
   * @return estado actual del repartidor (AVAILABLE, BUSY, OFFLINE)
   */
  public DriverStatus getStatus() {
    return status;
  }

  /**
   * @return pedido actualmente asignado, o {@code null} si no tiene
   */
  public Order getAssignedOrder() {
    return assignedOrder;
  }

  /**
   * Indica si el repartidor esta disponible para recibir un pedido.
   * Un repartidor esta disponible solo si su estado es
   * {@link DriverStatus#AVAILABLE}
   * y no tiene un pedido asignado.
   *
   * @return {@code true} si puede aceptar un nuevo pedido
   */
  public boolean isAvailable() {
    return status == DriverStatus.AVAILABLE && assignedOrder == null;
  }

  /**
   * Asigna un pedido al repartidor y cambia su estado a
   * {@link DriverStatus#BUSY}.
   *
   * @param order pedido a asignar
   * @throws IllegalArgumentException si {@code order} es {@code null}
   * @throws IllegalStateException    si el repartidor no esta disponible
   */
  public void assignOrder(Order order) {
    if (order == null)
      throw new IllegalArgumentException("order cannot be null");
    if (!isAvailable())
      throw new IllegalStateException("Driver is not available");
    this.assignedOrder = order;
    this.status = DriverStatus.BUSY;
  }

  /**
   * Finaliza el pedido actual del repartidor. Libera la asignacion
   * y restaura el estado a {@link DriverStatus#AVAILABLE}.
   *
   * @throws IllegalStateException si el repartidor no tiene un pedido asignado
   */
  public void completeOrder() {
    if (this.assignedOrder == null)
      throw new IllegalStateException("Driver has no assigned order");
    this.assignedOrder = null;
    this.status = DriverStatus.AVAILABLE;
  }

  /**
   * Establece manualmente el estado del repartidor.
   *
   * @param status nuevo estado
   * @throws IllegalArgumentException si {@code status} es {@code null}
   */
  public void setStatus(DriverStatus status) {
    if (status == null)
      throw new IllegalArgumentException("status cannot be null");
    this.status = status;
  }

  /**
   * Actualiza la ubicacion actual del repartidor.
   *
   * @param newLocation nueva ubicacion
   * @throws IllegalArgumentException si {@code newLocation} es {@code null}
   */
  public void updateLocation(Node newLocation) {
    if (newLocation == null)
      throw new IllegalArgumentException("newLocation cannot be null");
    this.currentLocation = newLocation;
  }

  /**
   * @return representacion textual del repartidor
   */
  @Override
  public String toString() {
    return "DeliveryDriver{name='" + name + "', status=" + status + ", hasOrder=" + (assignedOrder != null) + "}";
  }

  /**
   * Compara repartidores por su nombre.
   *
   * @param o objeto a comparar
   * @return {@code true} si ambos repartidores tienen el mismo {@code name}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof DeliveryDriver))
      return false;
    DeliveryDriver that = (DeliveryDriver) o;
    return Objects.equals(name, that.name);
  }

  /**
   * @return hash basado en el nombre del repartidor
   */
  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

}
