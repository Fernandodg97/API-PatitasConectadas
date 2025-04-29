package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.models.SeguidoModel;
import net.xeill.elpuig.apipatitasconectadas.services.SeguidoService;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/seguidos")
public class SeguidoController {

    @Autowired
    private SeguidoService seguidoService;

    // Obtener todos los usuarios que sigue un usuario
    @GetMapping
    public ResponseEntity<?> obtenerSeguidos(@PathVariable Long usuarioId) {
        List<SeguidoModel> seguidos = seguidoService.obtenerSeguidos(usuarioId);
        return ResponseEntity.ok(seguidos);
    }

    // Seguir a un usuario
    @PostMapping("/{usuarioASeguirId}")
    public ResponseEntity<?> seguirUsuario(@PathVariable Long usuarioId, @PathVariable Long usuarioASeguirId) {
        try {
            SeguidoModel nuevo = seguidoService.seguirUsuario(usuarioId, usuarioASeguirId);
            return ResponseEntity.ok(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Dejar de seguir a un usuario
    @DeleteMapping("/{usuarioASeguirId}")
    public ResponseEntity<?> dejarDeSeguir(@PathVariable Long usuarioId, @PathVariable Long usuarioASeguirId) {
        boolean eliminado = seguidoService.dejarDeSeguir(usuarioId, usuarioASeguirId);
        if (eliminado) {
            return ResponseEntity.ok("Has dejado de seguir al usuario con ID: " + usuarioASeguirId);
        } else {
            return ResponseEntity.status(404).body("No se encontró una relación de seguimiento.");
        }
    }
}
