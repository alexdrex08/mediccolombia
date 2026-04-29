package com.sena.meciccolombia.mediccolombia.domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="EstadoUsuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoUsuario implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String observacion;
    private java.time.LocalDateTime fechaInicio;
    private java.time.LocalDateTime fechaFin;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name ="idTipoEstado")
    private TipoEstado tipoEstado;
}