package com.ds.core.models;

import java.util.Objects;

/**
 * Representa un repartidor del sistema.
 */
public class DeliveryDriver {

    private final String name;
    private Node currentLocation;
    private DriverStatus status;

    /**
     * Constructor del repartidor.
     *
     * @param name nombre del repartidor
     * @param currentLocation ubicación inicial
     */
    public DeliveryDriver(String name, Node currentLocation) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (currentLocation == null) {
            throw new IllegalArgumentException("La ubicación no puede ser nula");
        }

        this.name = name;
        this.currentLocation = currentLocation;
        this.status = DriverStatus.AVAILABLE;
    }

    public String getName() {
        return name;
    }

    public Node getCurrentLocation() {
        return currentLocation;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public boolean isAvailable() {
        return status == DriverStatus.AVAILABLE;
    }

    public void setStatus(DriverStatus status) {

        if (status == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }

        this.status = status;
    }

    public void updateLocation(Node newLocation) {

        if (newLocation == null) {
            throw new IllegalArgumentException("La nueva ubicación no puede ser nula");
        }

        this.currentLocation = newLocation;
    }

    @Override
    public String toString() {
        return "DeliveryDriver{" +
                "name='" + name + '\'' +
                ", currentLocation=" + currentLocation +
                ", status=" + status +
                '}';
    }
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DeliveryDriver)) return false;
    DeliveryDriver that = (DeliveryDriver) o;
    return name.equals(that.name);
}

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

