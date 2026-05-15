package com.ds.core.validators;

import com.ds.core.models.Client;

/**
 * Validador de clientes.
 * <p>
 * Verifica que los datos de un {@link Client} sean correctos:
 * campos obligatorios, formato de telefono, direccion y ubicacion.
 * </p>
 */
public class ClientValidator {

  private static final String PHONE_REGEX = "^\\+?[0-9]{7,15}$";

  private ClientValidator() {
  }

  /**
   * Valida que los campos del cliente sean consistentes.
   *
   * @param client cliente a validar
   * @throws IllegalArgumentException si el cliente es invalido
   */
  public static void validate(Client client) {
    if (client == null)
      throw new IllegalArgumentException("Client cannot be null");
    if (client.getId() == null || client.getId().isBlank())
      throw new IllegalArgumentException("Client id cannot be blank");
    if (client.getName() == null || client.getName().isBlank())
      throw new IllegalArgumentException("Client name cannot be blank");
    if (client.getPhone() == null || client.getPhone().isBlank())
      throw new IllegalArgumentException("Client phone cannot be blank");
    if (client.getAddress() == null || client.getAddress().isBlank())
      throw new IllegalArgumentException("Client address cannot be blank");
    if (client.getLocation() == null)
      throw new IllegalArgumentException("Client location cannot be null");
  }

  /**
   * Valida el formato del numero de telefono.
   * Acepta numeros de 7 a 15 digitos, con o sin prefijo "+".
   *
   * @param phone numero de telefono a validar
   * @return {@code true} si el formato es valido
   */
  public static boolean isValidPhone(String phone) {
    return phone != null && phone.matches(PHONE_REGEX);
  }

  /**
   * Valida completamente el cliente incluyendo el formato del telefono.
   *
   * @param client cliente a validar
   * @throws IllegalArgumentException si algun campo es invalido
   */
  public static void validateFull(Client client) {
    validate(client);
    if (!isValidPhone(client.getPhone()))
      throw new IllegalArgumentException("Invalid phone format: " + client.getPhone());
  }
}
