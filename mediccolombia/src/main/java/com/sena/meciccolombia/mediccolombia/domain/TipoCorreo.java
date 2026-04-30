package com.sena.meciccolombia.mediccolombia.domain;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TipoCorreo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoCorreo implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tipo_correo")
    private Long id;

    private String nombreTipo;

    @OneToMany(mappedBy ="tipoCorreo", cascade = CascadeType.ALL, orphanRemoval =  true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Correo> correos;

}
