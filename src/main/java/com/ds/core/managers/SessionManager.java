package com.ds.core.managers;

import com.ds.core.models.Order;
import com.ds.core.utils.Constants;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestiona el estado global de la sesion del sistema.
 * <p>
 * Mantiene la configuracion activa, las estadisticas de la
 * sesion actual y el estado de inicializacion de los modulos.
 * </p>
 */
public class SessionManager {

  private boolean initialized;
  private LocalDateTime sessionStart;
  private int totalOrdersProcessed;
  private int totalDeliveriesCompleted;
  private final Map<String, Object> config;

  /**
   * Construye el gestor de sesion con valores iniciales por defecto.
   */
  public SessionManager() {
    this.initialized = false;
    this.totalOrdersProcessed = 0;
    this.totalDeliveriesCompleted = 0;
    this.config = new HashMap<>();
    loadDefaultConfig();
  }

  private void loadDefaultConfig() {
    config.put("averageSpeedKmh", Constants.DEFAULT_AVERAGE_SPEED_KMH);
    config.put("preparationTimeMin", Constants.DEFAULT_PREPARATION_TIME_MIN);
    config.put("maxActiveOrders", Constants.MAX_ACTIVE_ORDERS);
    config.put("nearbySearchRadiusKm", Constants.NEARBY_SEARCH_RADIUS_KM);
    config.put("maxPriority", Constants.MAX_PRIORITY);
  }

  /**
   * Inicializa la sesion del sistema.
   */
  public void startSession() {
    this.initialized = true;
    this.sessionStart = LocalDateTime.now();
    this.totalOrdersProcessed = 0;
    this.totalDeliveriesCompleted = 0;
  }

  /**
   * Finaliza la sesion actual.
   */
  public void endSession() {
    this.initialized = false;
  }

  /**
   * @return {@code true} si el sistema esta inicializado
   */
  public boolean isInitialized() {
    return initialized;
  }

  /**
   * @return timestamp de inicio de sesion
   */
  public LocalDateTime getSessionStart() {
    return sessionStart;
  }

  /**
   * Registra un pedido procesado.
   */
  public void registerOrderProcessed() {
    totalOrdersProcessed++;
  }

  /**
   * Registra una entrega completada.
   */
  public void registerDeliveryCompleted() {
    totalDeliveriesCompleted++;
  }

  /**
   * @return total de pedidos procesados en la sesion
   */
  public int getTotalOrdersProcessed() {
    return totalOrdersProcessed;
  }

  /**
   * @return total de entregas completadas en la sesion
   */
  public int getTotalDeliveriesCompleted() {
    return totalDeliveriesCompleted;
  }

  /**
   * Obtiene un valor de configuracion.
   *
   * @param key clave de configuracion
   * @return valor asociado, o {@code null} si no existe
   */
  public Object getConfig(String key) {
    return config.get(key);
  }

  /**
   * Establece un valor de configuracion.
   *
   * @param key   clave de configuracion
   * @param value valor a asignar
   */
  public void setConfig(String key, Object value) {
    config.put(key, value);
  }

  /**
   * @return tiempo transcurrido de la sesion en minutos
   */
  public long getSessionDurationMinutes() {
    if (sessionStart == null) return 0;
    return java.time.Duration.between(sessionStart, LocalDateTime.now()).toMinutes();
  }

  /**
   * Reinicia las estadisticas de la sesion.
   */
  public void resetStats() {
    totalOrdersProcessed = 0;
    totalDeliveriesCompleted = 0;
  }
}