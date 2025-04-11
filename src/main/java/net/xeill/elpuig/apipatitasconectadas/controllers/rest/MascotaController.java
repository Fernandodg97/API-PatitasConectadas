package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.models.MascotaModel;
import net.xeill.elpuig.apipatitasconectadas.services.MascotaService;

@RestController
@RequestMapping("/usuarios/{usuarioId}/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    // Obtener todas las mascotas de un usuario
    @GetMapping
    public ResponseEntity<?> getMascotasByUsuario(@PathVariable("usuarioId") Long usuarioId) {
        List<MascotaModel> mascotas = mascotaService.getMascotasByUsuario(usuarioId);
        return ResponseEntity.ok(mascotas);
    }

    // Guardar una nueva mascota para un usuario
    @PostMapping
    public ResponseEntity<?> saveMascota(@PathVariable("usuarioId") Long usuarioId, @RequestBody MascotaModel mascota) {
        try {
            mascota.setUsuarioId(usuarioId); // Aseguramos que se asigne el ID del usuario
            MascotaModel saved = mascotaService.saveMascota(mascota);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al guardar la mascota: " + e.getMessage());
        }
    }

    // Obtener una mascota específica de un usuario
    @GetMapping("/{mascotaId}")
    public ResponseEntity<?> getMascotaById(@PathVariable("usuarioId") Long usuarioId,
                                            @PathVariable("mascotaId") Long mascotaId) {
        Optional<MascotaModel> mascota = mascotaService.getByIdAndUsuarioId(mascotaId, usuarioId);
        return mascota.isPresent()
            ? ResponseEntity.ok(mascota.get())
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mascota no encontrada con ID: " + mascotaId);
    }

    // Actualizar una mascota específica de un usuario
    @PutMapping("/{mascotaId}")
    public ResponseEntity<?> updateMascota(@PathVariable("usuarioId") Long usuarioId,
                                           @PathVariable("mascotaId") Long mascotaId,
                                           @RequestBody MascotaModel request) {
        try {
            MascotaModel updated = mascotaService.updateByIdAndUsuarioId(request, mascotaId, usuarioId);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al actualizar la mascota: " + e.getMessage());
        }
    }

    // Eliminar una mascota específica de un usuario
    @DeleteMapping("/{mascotaId}")
    public ResponseEntity<?> deleteMascota(@PathVariable("usuarioId") Long usuarioId,
                                           @PathVariable("mascotaId") Long mascotaId) {
        boolean deleted = mascotaService.deleteMascotaByUsuario(mascotaId, usuarioId);
        if (deleted) {
            return ResponseEntity.ok("Mascota eliminada con ID: " + mascotaId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mascota no encontrada o no pertenece al usuario");
        }
    }
}
