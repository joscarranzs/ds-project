package com.ds.core.utils;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Generador de identificadores unicos para las entidades del sistema.
 * <p>
 * Soporta dos modos: IDs secuenciales con prefijo (ORDER-001, CLIENT-001)
 * y IDs basados en {@link UUID}.
 * </p>
 */
public class IdGenerator {

  private static final AtomicLong orderCounter = new AtomicLong(0);
  private static final AtomicLong clientCounter = new AtomicLong(0);
  private static final AtomicLong routeCounter = new AtomicLong(0);
  private static final AtomicLong driverCounter = new AtomicLong(0);

  private IdGenerator() {
  }

  /**
   * Genera un ID secuencial para un pedido.
   * Formato: "ORD-" seguido del numero correlativo.
   *
   * @return ID unico para el pedido
   */
  public static String newOrderId() {
    return "ORD-" + String.format("%03d", orderCounter.incrementAndGet());
  }

  /**
   * Genera un ID secuencial para un cliente.
   * Formato: "CLI-" seguido del numero correlativo.
   *
   * @return ID unico para el cliente
   */
  public static String newClientId() {
    return "CLI-" + String.format("%03d", clientCounter.incrementAndGet());
  }

  /**
   * Genera un ID secuencial para una ruta.
   * Formato: "RTE-" seguido del numero correlativo.
   *
   * @return ID unico para la ruta
   */
  public static String newRouteId() {
    return "RTE-" + String.format("%03d", routeCounter.incrementAndGet());
  }

  /**
   * Genera un ID secuencial para un repartidor.
   * Formato: "DRV-" seguido del numero correlativo.
   *
   * @return ID unico para el repartidor
   */
  public static String newDriverId() {
    return "DRV-" + String.format("%03d", driverCounter.incrementAndGet());
  }

  /**
   * Genera un ID aleatorio basado en UUID.
   *
   * @return ID unico universal
   */
  public static String newUuid() {
    return UUID.randomUUID().toString();
  }

  /**
   * Reinicia todos los contadores secuenciales a cero.
   */
  public static void reset() {
    orderCounter.set(0);
    clientCounter.set(0);
    routeCounter.set(0);
    driverCounter.set(0);
  }
}