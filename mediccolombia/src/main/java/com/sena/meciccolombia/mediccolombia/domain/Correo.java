package com.sena.meciccolombia.mediccolombia.domain;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Correo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Correo implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_correo")
    private Long id;

    private String correoElectronico;

    @ManyToOne
    @JoinColumn(name ="cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name ="proveedor_id")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name ="tipo_correo_id")
    private TipoCorreo tipoCorreo;

}
