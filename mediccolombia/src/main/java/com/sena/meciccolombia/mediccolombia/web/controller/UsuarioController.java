package com.sena.meciccolombia.mediccolombia.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.meciccolombia.mediccolombia.service.UsuarioService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.UsuarioCambiarContrasenaDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.UsuarioCreateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.UsuarioUpdateRequest;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioDetalleMovimientosResponseDto;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.UsuarioResponseDTO;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;





@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@RequestBody UsuarioCreateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable Long id, @RequestBody UsuarioUpdateRequest dto) {
        return ResponseEntity.ok(usuarioService.actualizar(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorCorreo(@PathVariable String correo) {
        return ResponseEntity.ok(usuarioService.buscarPorCorreo(correo));
    }
    @PatchMapping("/{id}/cambiar-contrasena")
    public ResponseEntity<Void> cambiarContrasena(@PathVariable Long id,@RequestBody UsuarioCambiarContrasenaDTO dto){
        usuarioService.cambiarContrasena(id, dto);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/estados")
    public ResponseEntity<UsuarioDetalleResponseDTO> obtenerDetalleEstados(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerDetalle(id));
    }
    @GetMapping("/{id}/movimientos")
    public ResponseEntity<UsuarioDetalleMovimientosResponseDto> obtenerDetalleMovimiento(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerDetalleMovimiento(id));
    }

    
}
