package com.ds.core.services;

import com.ds.core.models.Client;
import com.ds.core.models.Node;
import com.ds.core.utils.IdGenerator;
import com.ds.core.validators.ClientValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de gestion de clientes.
 * <p>
 * Proporciona operaciones CRUD para clientes, validacion de datos
 * y busqueda por distintos criterios.
 * </p>
 */
public class ClientService {

  private final List<Client> clients;

  /**
   * Construye el servicio con una lista vacia de clientes.
   */
  public ClientService() {
    this.clients = new ArrayList<>();
  }

  /**
   * Registra un nuevo cliente en el sistema.
   *
   * @param name     nombre del cliente
   * @param phone    numero de telefono
   * @param address  direccion fisica
   * @param location nodo del grafo que representa su ubicacion
   * @return cliente registrado
   */
  public Client registerClient(String name, String phone, String address, Node location) {
    String id = IdGenerator.newClientId();
    Client client = new Client(id, name, phone, address, location);
    ClientValidator.validateFull(client);
    clients.add(client);
    return client;
  }

  /**
   * Busca un cliente por su identificador.
   *
   * @param clientId identificador del cliente
   * @return Optional con el cliente encontrado
   */
  public Optional<Client> findById(String clientId) {
    return clients.stream()
        .filter(c -> c.getId().equals(clientId))
        .findFirst();
  }

  /**
   * Busca clientes por nombre (coincidencia parcial, case-insensitive).
   *
   * @param name nombre o fragmento a buscar
   * @return lista de clientes que coinciden
   */
  public List<Client> findByName(String name) {
    return clients.stream()
        .filter(c -> c.getName().toLowerCase().contains(name.toLowerCase()))
        .collect(java.util.stream.Collectors.toList());
  }

  /**
   * Busca clientes por ubicacion (nodo).
   *
   * @param locationId identificador del nodo
   * @return Optional con el cliente en esa ubicacion
   */
  public Optional<Client> findByLocation(String locationId) {
    return clients.stream()
        .filter(c -> c.getLocation().getId().equals(locationId))
        .findFirst();
  }

  /**
   * Retorna todos los clientes registrados.
   *
   * @return lista de clientes
   */
  public List<Client> getAllClients() {
    return new ArrayList<>(clients);
  }

  /**
   * @return cantidad de clientes registrados
   */
  public int count() {
    return clients.size();
  }
}