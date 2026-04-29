package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ReporteInv")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteInv implements Serializable{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaGeneracion;
    private String tipoResultado;
    private String resultado;

    @ManyToOne
    @JoinColumn(name = "idFiltroBusqueda")
    private FiltroBusqueda filtroBusqueda;

    @ManyToOne
    @JoinColumn(name ="idUsuario")
    private Usuario usuario;

    @OneToMany(mappedBy ="reporteInv", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlertaInv> alertas;


    
}
