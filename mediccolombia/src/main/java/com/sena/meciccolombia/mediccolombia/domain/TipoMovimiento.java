package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TipoMovimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoMovimiento implements Serializable{

    private static final long serialVersionUID =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tipo_movimiento")
    private Long id;

    @Column(name="nombre_movimiento")
    private String nombreMovimiento;
    @Column(name="descripcion")
    private String descripcion;

    @Column(name="signo")
    private int signo;

    @OneToMany(mappedBy = "tipoMovimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<MovimientoProd> movimiemtos;

    
}
