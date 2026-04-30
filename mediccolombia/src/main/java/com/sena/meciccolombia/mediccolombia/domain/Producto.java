package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_producto")
    private Long id;

    @Column(name ="nombre_prod", nullable = false)
    private String nombreProducto;

    @Column(name ="fecha_expiracion", nullable = false)
    private LocalDateTime fechaExpiracion;

    @Column(name ="stock", nullable = false)
    private Integer stock;

    @Column(name ="lote_producto", nullable = false)
    private String lote;

    @Column(name ="stock_minimo", nullable = false)
    private Integer stockMinimo;

    @Column(name ="stock_maximo", nullable = false)
    private Integer stockMaximo;

    @CreatedDate
    @Column(name ="fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaIngreso;

    @LastModifiedDate
    @Column(name ="fecha_modificacion", nullable = false)
    private LocalDateTime fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

     @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<MovimientoProd> movimientos;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<DetalleProveedorProducto> proveedores;


}
