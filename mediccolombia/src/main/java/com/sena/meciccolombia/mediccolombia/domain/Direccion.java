package com.sena.meciccolombia.mediccolombia.domain;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Direccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Direccion implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_direccion")
    private Long id;

    @Column(name="direccion", nullable = false)
    private String direccion;
    private String complemento;

    @ManyToOne 
    @JoinColumn(name ="cliente_id")
    private Cliente cliente;

    @ManyToOne 
    @JoinColumn(name ="proveedor_id")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name ="tipo_direccion_id")
    private TipoDireccion tipoDireccion;

    @ManyToOne  
    @JoinColumn(name ="barrio_id")
    private BarrioDireccion barrio;

}
