package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "DetalleProveedorProducto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleProveedorProducto implements Serializable{

    private static final long serialVersionUID =1L;

    @EmbeddedId
    private DetalleId id;

    @Column(name ="precio_unitario")
    private BigDecimal precioUnitario;

    @ManyToOne
    @MapsId("proveedorId")
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name ="producto_id")
    private Producto producto;

}
