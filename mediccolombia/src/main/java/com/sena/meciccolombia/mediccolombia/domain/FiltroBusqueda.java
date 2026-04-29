package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "FiltroBusqueda")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FiltroBusqueda implements Serializable{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String descripcion;

    @OneToMany(mappedBy = "filtroBusqueda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFiltro> detalles;

    @OneToMany(mappedBy ="filtroBusqueda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReporteInv> reportes;
    
}
