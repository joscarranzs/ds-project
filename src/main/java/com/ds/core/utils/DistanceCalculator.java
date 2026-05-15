package com.ds.core.utils;

/**
 * Utilidades para el calculo de distancias entre puntos.
 * <p>
 * Soporta distintos metodos de calculo: distancia euclidiana,
 * Manhattan y distancia en linea recta. Las coordenadas se
 * representan como pares (x, y) en un plano cartesiano.
 * </p>
 */
public class DistanceCalculator {

  private DistanceCalculator() {
  }

  /**
   * Calcula la distancia euclidiana entre dos puntos.
   * d = sqrt((x2 - x1)^2 + (y2 - y1)^2)
   *
   * @param x1 coordenada x del primer punto
   * @param y1 coordenada y del primer punto
   * @param x2 coordenada x del segundo punto
   * @param y2 coordenada y del segundo punto
   * @return distancia euclidiana
   */
  public static double euclidean(double x1, double y1, double x2, double y2) {
    double dx = x2 - x1;
    double dy = y2 - y1;
    return Math.sqrt(dx * dx + dy * dy);
  }

  /**
   * Calcula la distancia Manhattan entre dos puntos.
   * d = |x2 - x1| + |y2 - y1|
   *
   * @param x1 coordenada x del primer punto
   * @param y1 coordenada y del primer punto
   * @param x2 coordenada x del segundo punto
   * @param y2 coordenada y del segundo punto
   * @return distancia Manhattan
   */
  public static double manhattan(double x1, double y1, double x2, double y2) {
    return Math.abs(x2 - x1) + Math.abs(y2 - y1);
  }

  /**
   * Calcula la distancia euclidiana y la convierte a kilometros
   * usando un factor de escala. Util cuando las coordenadas
   * representan posiciones en un mapa simplificado.
   *
   * @param x1    coordenada x del primer punto
   * @param y1    coordenada y del primer punto
   * @param x2    coordenada x del segundo punto
   * @param y2    coordenada y del segundo punto
   * @param scaleFactor factor de conversion a kilometros
   * @return distancia en kilometros
   */
  public static double toKilometers(double x1, double y1, double x2, double y2, double scaleFactor) {
    return euclidean(x1, y1, x2, y2) * scaleFactor;
  }
}