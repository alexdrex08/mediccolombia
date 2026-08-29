package com.sena.meciccolombia.mediccolombia.web.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoCreateRequestDto {

    private String nombreProducto;

    private LocalDateTime fechaExpiracion;

    private Integer stock;

    @NotBlank(message = "El lote es obligatorio")
    @Pattern(regexp = "^LOTE-\\d{8}-[A-Z0-9]{2,4}$", message = "El lote debe tener el formato LOTE-YYYYMMDD-XXX (ej: LOTE-20250101-ABC)")
    private String lote;

    private Integer stockMinimo;

    private Integer stockMaximo;

    private Long idCategoria;

    private Long idUsuario;

}
