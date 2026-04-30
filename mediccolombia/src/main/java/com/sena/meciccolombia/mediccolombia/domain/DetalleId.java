package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DetalleId implements Serializable {
    private Long proveedorId;
    private Long productoId;

    // Constructores, Getters y Setters
    public DetalleId() {}
    public DetalleId(Long proveedorId, Long productoId) {
        this.proveedorId = proveedorId;
        this.productoId = productoId;
    }

    // ¡OBLIGATORIO! Implementar equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalleId that = (DetalleId) o;
        return Objects.equals(proveedorId, that.proveedorId) && 
               Objects.equals(productoId, that.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proveedorId, productoId);
    }
}