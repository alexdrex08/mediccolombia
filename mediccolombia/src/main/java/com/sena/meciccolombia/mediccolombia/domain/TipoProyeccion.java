package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TipoProyeccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoProyeccion implements Serializable{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreProyeccion;

    @OneToMany(mappedBy ="tipoProyeccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proyecciones> proyecciones;

}
