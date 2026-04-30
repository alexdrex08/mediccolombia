package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "FiltroBusqueda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FiltroBusqueda implements Serializable{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_filtro_busqueda")
    private Long id;
    
    private String descripcion;

    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "filtroBusqueda", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<DetalleFiltro> detalleFiltro;

    @OneToMany(mappedBy ="filtroBusqueda", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ReporteInv> reporteInv;
    
}
