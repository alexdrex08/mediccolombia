package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "MovimientoProd")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoProd implements Serializable {

    private static final long serialVersionUID =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_movimiento_inv")
    private Long id;

    private LocalDateTime fechaMovimiento;

    @Column(name ="cantidad_desplazada")
    private Integer cantidad;

    @Column(name ="motivo_repor")
    private String movimiento;

    @Column(name ="picker_checker")
    private String pickerChecker;

    @ManyToOne
    @JoinColumn(name ="tipo_movimiento_id")
    private TipoMovimiento tipoMovimiento;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name ="usuario_id")
    private Usuario usuario;

    
}
