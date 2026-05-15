package com.ds.core.utils;

import java.util.concurrent.TimeUnit;

/**
 * Utilidades para formateo y conversion de tiempos.
 * <p>
 * Convierte minutos a formatos legibles (horas, minutos,
 * representacion textual) y viceversa.
 * </p>
 */
public class TimeFormatter {

  private TimeFormatter() {
  }

  /**
   * Convierte minutos a un arreglo {horas, minutos}.
   *
   * @param totalMinutes tiempo total en minutos (no negativo)
   * @return arreglo de 2 elementos: [horas, minutos]
   */
  public static int[] toHoursAndMinutes(double totalMinutes) {
    if (totalMinutes < 0)
      throw new IllegalArgumentException("totalMinutes cannot be negative");
    int hours = (int) totalMinutes / 60;
    int minutes = (int) totalMinutes % 60;
    return new int[] { hours, minutes };
  }

  /**
   * Formatea minutos a una cadena legible (ej. "2h 30min").
   *
   * @param totalMinutes tiempo total en minutos
   * @return cadena formateada
   */
  public static String format(double totalMinutes) {
    if (totalMinutes < 0)
      throw new IllegalArgumentException("totalMinutes cannot be negative");
    int[] hm = toHoursAndMinutes(totalMinutes);
    if (hm[0] == 0)
      return hm[1] + "min";
    if (hm[1] == 0)
      return hm[0] + "h";
    return hm[0] + "h " + hm[1] + "min";
  }

  /**
   * Convierte horas a minutos.
   *
   * @param hours cantidad de horas
   * @return equivalente en minutos
   */
  public static double hoursToMinutes(double hours) {
    if (hours < 0)
      throw new IllegalArgumentException("hours cannot be negative");
    return hours * 60;
  }

  /**
   * Convierte minutos a horas.
   *
   * @param minutes cantidad de minutos
   * @return equivalente en horas
   */
  public static double minutesToHours(double minutes) {
    if (minutes < 0)
      throw new IllegalArgumentException("minutes cannot be negative");
    return minutes / 60.0;
  }

  /**
   * Convierte segundos a minutos.
   *
   * @param seconds cantidad de segundos
   * @return equivalente en minutos
   */
  public static double secondsToMinutes(double seconds) {
    if (seconds < 0)
      throw new IllegalArgumentException("seconds cannot be negative");
    return seconds / 60.0;
  }

  /**
   * Suma dos tiempos en minutos y retorna el resultado formateado.
   *
   * @param time1 minutos del primer tiempo
   * @param time2 minutos del segundo tiempo
   * @return cadena formateada con la suma
   */
  public static String sumAndFormat(double time1, double time2) {
    return format(time1 + time2);
  }
}
