package com.sena.meciccolombia.mediccolombia.web.controller.view;

import java.io.File;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sena.meciccolombia.mediccolombia.dao.UsuarioDAO;
import com.sena.meciccolombia.mediccolombia.domain.Usuario;
import com.sena.meciccolombia.mediccolombia.security.MyUserDetails;
import com.sena.meciccolombia.mediccolombia.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioFotoController {

    private final UsuarioService usuarioService;
    private final UsuarioDAO usuarioDAO;

    // ✅ Guardar en una carpeta 'uploads' en la raíz del proyecto
private static final String CARPETA_UPLOADS = System.getProperty("user.dir") + "/uploads/perfil/";

    @PostMapping("/{id}/foto")
    public ResponseEntity<?> subirFoto(
            @PathVariable Long id,
            @RequestParam("foto") MultipartFile archivo,
            Authentication auth) {

        try {
            // Validaciones de seguridad
            MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
            Long userId = userDetails.getId();
            String rol = userDetails.getRol();

            Usuario usuario = usuarioDAO.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (!userId.equals(id) && !"ADMIN".equals(rol)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permiso para modificar este usuario");
            }

            // Validar archivo
            if (archivo.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo está vacío");
            }

            // Validar tipo de archivo
            String contentType = archivo.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().body("Solo se permiten imágenes");
            }

            // Validar tamaño (máximo 2MB)
            if (archivo.getSize() > 2 * 1024 * 1024) {
                return ResponseEntity.badRequest().body("La imagen no debe superar los 2MB");
            }

            // Generar nombre único
            String extension = archivo.getOriginalFilename()
                    .substring(archivo.getOriginalFilename().lastIndexOf("."));
            String nombreArchivo = "perfil_" + id + "_" + System.currentTimeMillis() + extension;

            File directorio = new File(CARPETA_UPLOADS);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            // Guardar archivo
            File archivoDestino = new File(CARPETA_UPLOADS + nombreArchivo);
            archivo.transferTo(archivoDestino);

            // Guardar ruta en la base de datos
            String rutaRelativa = "/uploads/perfil/" + nombreArchivo;
            usuario.setFotoPerfil(rutaRelativa);
            usuarioDAO.save(usuario);

            return ResponseEntity.ok(Map.of(
                    "mensaje", "Foto actualizada correctamente",
                    "url", rutaRelativa));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir la foto: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}/foto")
    public ResponseEntity<?> eliminarFoto(@PathVariable Long id, Authentication auth) {
        // Validaciones similares al upload...
        Usuario usuario = usuarioDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setFotoPerfil(null);
        usuarioDAO.save(usuario);
        return ResponseEntity.ok(Map.of("mensaje", "Foto eliminada correctamente"));
    }
}