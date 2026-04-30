package com.sena.meciccolombia.mediccolombia.domain;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TipoDireccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoDireccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_tipo_direccion")
    private Long id;

    private String nombreTipo;

    @OneToMany(mappedBy = "tipoDireccion", cascade = CascadeType.ALL, orphanRemoval =  true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Direccion> direcciones;

}
