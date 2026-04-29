package com.sena.meciccolombia.mediccolombia.domain;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "AlertaInv")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertaInv implements Serializable {

    public static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaCreacion;
    private String tipoAlerta;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name="idProducto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name ="idReporte")
    private ReporteInv reporte;


}
