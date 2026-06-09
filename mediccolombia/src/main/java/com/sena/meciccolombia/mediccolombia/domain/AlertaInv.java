package com.sena.meciccolombia.mediccolombia.domain;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "AlertaInv")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertaInv implements Serializable {

    public static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_alerta")
    private Long id;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "tipo_alerta")
    private String tipoAlerta;

    @Column(name ="descripcion_alerta", length = 1000)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name="idProducto")
    private Producto producto;

    @OneToMany(mappedBy = "alertaInv", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ReporteInv> reportes;


}
