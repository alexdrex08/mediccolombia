package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TipoMovimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoMovimiento implements Serializable{

    private static final long serialVersionUID =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreMovimiento;
    private String descripcion;

    @OneToMany(mappedBy = "tipoMovimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovimientoProd> movimiemtos;

    
}
