package com.sena.meciccolombia.mediccolombia.domain;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Telefono")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Telefono implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_telefono")
    private Long id;

    private String numero;

    private String complemento;

    @ManyToOne
    @JoinColumn(name ="cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name ="proveedor_id")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "tipo_telefono_id")
    private TipoTelefono tipoTelefono;


}
