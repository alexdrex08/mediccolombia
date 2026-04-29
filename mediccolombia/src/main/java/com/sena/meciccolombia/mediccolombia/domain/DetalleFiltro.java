package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "DetalleFiltro")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleFiltro implements Serializable{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String valorFiltro;
    private String campoFiltro;
    private String tipoDato;

    @ManyToOne
    @JoinColumn(name ="idFiltroBusqueda")
    private FiltroBusqueda filtroBusqueda;

}
