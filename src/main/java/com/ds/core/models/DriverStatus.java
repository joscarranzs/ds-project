package com.ds.core.models;

/**
 * Estados operativos de un repartidor.
 * <p>
 * {@link #AVAILABLE} → puede recibir pedidos.
 * {@link #BUSY} → tiene un pedido activo.
 * {@link #OFFLINE} → no está disponible (fuera de horario, descanso, etc.).
 * </p>
 */
public enum DriverStatus {
  /** Repartidor libre para recibir pedidos */
  AVAILABLE,
  /** Repartidor con un pedido en curso */
  BUSY,
  /** Repartidor desconectado del sistema */
  OFFLINE
}
