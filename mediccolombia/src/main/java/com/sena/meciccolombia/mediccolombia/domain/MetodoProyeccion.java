package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "MetodoProyeccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetodoProyeccion implements Serializable{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreMetodo;

    @OneToMany(mappedBy ="metodoProyeccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proyecciones> proyecciones;

}
