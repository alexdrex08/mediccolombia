package com.sena.meciccolombia.mediccolombia.domain;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "DetallePedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePedido implements Serializable{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;
    private BigDecimal precioUnitario;

    @ManyToOne
    @JoinColumn(name ="idProducto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name ="idPedido")
    private PedidoCompra pedido;




}
