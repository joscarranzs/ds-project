package com.ds.core.models;

/**
 * Estados posibles del ciclo de vida de un pedido.
 * <p>
 * La transición esperada es:
 *
 * <pre>
 * PENDING → PREPARING → IN_TRANSIT → DELIVERED
 * Cualquier estado (excepto DELIVERED) → CANCELLED
 * </pre>
 * </p>
 */
public enum OrderStatus {
  /** Pedido creado, esperando ser preparado */
  PENDING,
  /** Pedido en preparación */
  PREPARING,
  /** Pedido en camino hacia el cliente */
  IN_TRANSIT,
  /** Pedido entregado al cliente */
  DELIVERED,
  /** Pedido cancelado */
  CANCELLED
}
