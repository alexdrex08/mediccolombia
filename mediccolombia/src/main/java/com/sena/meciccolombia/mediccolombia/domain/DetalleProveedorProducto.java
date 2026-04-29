package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "DetalleProveedorProducto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleProveedorProducto implements Serializable{

    private static final long serialVersionUID =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal precioUnitario;

    @ManyToOne
    @JoinColumn(name = "idProveedor")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name ="idProducto")
    private Producto producto;

}
