package com.sena.meciccolombia.mediccolombia.domain;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Telefono")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Telefono implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    @ManyToOne
    @JoinColumn(name ="idCliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idTipoTelefono")
    private TipoTelefono tipoTelefono;


}
