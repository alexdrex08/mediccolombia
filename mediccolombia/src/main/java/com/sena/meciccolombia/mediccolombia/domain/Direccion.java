package com.sena.meciccolombia.mediccolombia.domain;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Direccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Direccion implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccionX;
    private String complemento;

    @ManyToOne 
    @JoinColumn(name ="idCliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name ="idTipoDireccion")
    private TipoDireccion tipoDireccion;

    @ManyToOne
    @JoinColumn(name ="idBarrio")
    private BarrioDireccion barrio;

}
