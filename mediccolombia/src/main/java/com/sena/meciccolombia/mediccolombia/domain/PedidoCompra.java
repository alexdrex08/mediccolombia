package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PedidoCompra")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoCompra implements Serializable{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaPedido;
    private BigDecimal totalPedido;
    private String observacion;

    @ManyToOne
    @JoinColumn(name="idEstadoPedido")
    private EstadoPedido estadoPedido;

    @ManyToOne
    @JoinColumn(name="idProveedor")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;



}
