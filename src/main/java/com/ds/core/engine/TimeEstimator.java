package com.ds.core.engine;

import com.ds.core.models.Node;

import java.util.List;

/**
 * estima el tiempo de entrega de una orden basándose en la distancia de la ruta
 * y la velocidad promedio del repartidor.
 */
public class TimeEstimator {

    // velocidad promedio del repartidor en km/h
    private double averageSpeedKmh;

    // tiempo base de preparación en minutos (antes de salir)
    private double basePreparationMinutes;

    /**
     * constructor con velocidad y tiempo de preparación personalizados.
     *
     * @param averageSpeedKmh       velocidad promedio en km/h
     * @param basePreparationMinutes tiempo fijo de preparación en minutos
     */
    public TimeEstimator(double averageSpeedKmh, double basePreparationMinutes) {
        this.averageSpeedKmh = averageSpeedKmh;
        this.basePreparationMinutes = basePreparationMinutes;
    }

    /**
     * constructor por defecto: 30 km/h y 10 minutos de preparación.
     */
    public TimeEstimator() {
        this(30.0, 10.0);
    }

    /**
     * estima el tiempo total de entrega para una ruta dada.
     *
     * @param route    lista de nodos que conforman la ruta
     * @param distance distancia total de la ruta (en km)
     * @return tiempo estimado en minutos
     */
    public double estimateTime(List<Node> route, double distance) {
        if (route == null || route.isEmpty()) {
            return 0.0;
        }

        // tiempo en ruta = distancia / velocidad * 60 (convertir a minutos)
        double travelTime = (distance / averageSpeedKmh) * 60.0;

        return basePreparationMinutes + travelTime;
    }

    /**
     * estima el tiempo usando solo la distancia, sin lista de nodos.
     *
     * @param distanceKm distancia en kilómetros
     * @return tiempo estimado en minutos
     */
    public double estimateTimeFromDistance(double distanceKm) {
        double travelTime = (distanceKm / averageSpeedKmh) * 60.0;
        return basePreparationMinutes + travelTime;
    }

    /**
     * formatea el tiempo en minutos a un string legible (ej: "25 min" o "1h 10min").
     *
     * @param minutes tiempo en minutos
     * @return string formateado
     */
    public String formatTime(double minutes) {
        int total = (int) Math.round(minutes);
        if (total < 60) {
            return total + " min";
        }
        int hours = total / 60;
        int mins = total % 60;
        return hours + "h " + mins + "min";
    }

    // getters y setters por si se necesita ajustar la configuración

    public double getAverageSpeedKmh() {
        return averageSpeedKmh;
    }

    public void setAverageSpeedKmh(double averageSpeedKmh) {
        this.averageSpeedKmh = averageSpeedKmh;
    }

    public double getBasePreparationMinutes() {
        return basePreparationMinutes;
    }

    public void setBasePreparationMinutes(double basePreparationMinutes) {
        this.basePreparationMinutes = basePreparationMinutes;
    }
}