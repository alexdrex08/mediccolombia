package com.sena.meciccolombia.mediccolombia.domain;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TipoDireccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoDireccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreTipo;

    @OneToMany(mappedBy = "tipoDireccion", cascade = CascadeType.ALL, orphanRemoval =  true)
    private List<Direccion> direcciones;

}
