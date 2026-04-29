package com.sena.meciccolombia.mediccolombia.domain;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "VentaRegistro")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaRegistro implements Serializable{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaVenta;
    private BigDecimal totalVenta;

    @ManyToOne
    @JoinColumn(name="idCliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles;

}
