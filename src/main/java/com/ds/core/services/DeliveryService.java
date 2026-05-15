package com.ds.core.services;

import com.ds.core.models.DeliveryDriver;
import com.ds.core.models.Node;
import com.ds.core.models.Order;
import com.ds.core.utils.IdGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio de gestion de entregas.
 * <p>
 * Administra los repartidores, asigna pedidos a repartidores
 * disponibles y realiza el seguimiento del progreso de las entregas.
 * </p>
 */
public class DeliveryService {

  private final List<DeliveryDriver> drivers;

  /**
   * Construye el servicio con una lista vacia de repartidores.
   */
  public DeliveryService() {
    this.drivers = new ArrayList<>();
  }

  /**
   * Registra un nuevo repartidor en el sistema.
   *
   * @param name            nombre del repartidor
   * @param currentLocation ubicacion inicial
   * @return repartidor registrado
   */
  public DeliveryDriver registerDriver(String name, Node currentLocation) {
    DeliveryDriver driver = new DeliveryDriver(name, currentLocation);
    drivers.add(driver);
    return driver;
  }

  /**
   * Asigna un pedido al primer repartidor disponible.
   *
   * @param order pedido a asignar
   * @return Optional con el repartidor asignado
   */
  public Optional<DeliveryDriver> assignOrder(Order order) {
    return findAvailableDriver().map(driver -> {
      driver.assignOrder(order);
      return driver;
    });
  }

  /**
   * Marca un pedido como completado para el repartidor asignado.
   *
   * @param driverName nombre del repartidor
   * @return Optional con el repartidor actualizado
   */
  public Optional<DeliveryDriver> completeDelivery(String driverName) {
    return findByName(driverName).map(driver -> {
      driver.completeOrder();
      return driver;
    });
  }

  /**
   * Actualiza la ubicacion de un repartidor.
   *
   * @param driverName  nombre del repartidor
   * @param newLocation nueva ubicacion
   * @return Optional con el repartidor actualizado
   */
  public Optional<DeliveryDriver> updateLocation(String driverName, Node newLocation) {
    return findByName(driverName).map(driver -> {
      driver.updateLocation(newLocation);
      return driver;
    });
  }

  /**
   * Busca el primer repartidor disponible.
   *
   * @return Optional con el repartidor disponible
   */
  public Optional<DeliveryDriver> findAvailableDriver() {
    return drivers.stream()
        .filter(DeliveryDriver::isAvailable)
        .findFirst();
  }

  /**
   * Retorna todos los repartidores disponibles.
   *
   * @return lista de repartidores disponibles
   */
  public List<DeliveryDriver> getAvailableDrivers() {
    return drivers.stream()
        .filter(DeliveryDriver::isAvailable)
        .collect(Collectors.toList());
  }

  /**
   * Busca un repartidor por su nombre.
   *
   * @param name nombre del repartidor
   * @return Optional con el repartidor encontrado
   */
  public Optional<DeliveryDriver> findByName(String name) {
    return drivers.stream()
        .filter(d -> d.getName().equals(name))
        .findFirst();
  }

  /**
   * Retorna todos los repartidores registrados.
   *
   * @return lista de repartidores
   */
  public List<DeliveryDriver> getAllDrivers() {
    return new ArrayList<>(drivers);
  }

  /**
   * @return cantidad de repartidores
   */
  public int count() {
    return drivers.size();
  }
}
