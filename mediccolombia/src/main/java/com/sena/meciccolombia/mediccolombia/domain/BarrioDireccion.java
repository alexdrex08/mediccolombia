package com.sena.meciccolombia.mediccolombia.domain;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BarrioDireccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BarrioDireccion implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreBarrio;

    @OneToMany(mappedBy = "barrio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Direccion> direcciones;

}
