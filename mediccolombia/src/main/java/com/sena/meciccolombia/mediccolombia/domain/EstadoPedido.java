package com.sena.meciccolombia.mediccolombia.domain;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "EstadoPedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoPedido implements Serializable{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreEstado;
    private String descripcion;

    @OneToMany(mappedBy ="estadoPedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoCompra> pedidos;


    
}
