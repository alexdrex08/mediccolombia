package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "MovimientoProd")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoProd implements Serializable {

    private static final long serialVersionUID =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaMovimiento;
    private Integer cantidad;
    private String movimiento;

    @ManyToOne
    @JoinColumn(name ="idTipoMovimiento")
    private TipoMovimiento tipoMovimiento;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name ="idUsuario")
    private Usuario usuario;

    
}
