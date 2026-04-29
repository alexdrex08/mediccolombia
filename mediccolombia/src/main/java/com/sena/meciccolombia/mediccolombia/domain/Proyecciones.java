package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Proyecciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proyecciones implements Serializable{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String resultadoProyeccion;
    private String referenciaTipo;
    private Integer pedidosEstimados;
    private LocalDateTime fechaGeneracion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    @ManyToOne
    @JoinColumn(name ="idMetodoProyeccion")
    private MetodoProyeccion metodoProyeccion;

    @ManyToOne
    @JoinColumn(name ="idTipoProyeccion")
    private TipoProyeccion tipoProyeccion;

    @ManyToOne
    @JoinColumn(name ="idProducto")
    private Producto producto;


}
