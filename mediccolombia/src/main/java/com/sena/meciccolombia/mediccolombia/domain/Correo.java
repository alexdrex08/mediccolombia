package com.sena.meciccolombia.mediccolombia.domain;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Correo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Correo implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String correoElectronico;

    @ManyToOne
    @JoinColumn(name ="idCliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name ="idTipoCorreo")
    private TipoCorreo tipoCorreo;

}
