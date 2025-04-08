package net.xeill.elpuig.apipatitasconectadas.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.models.MascotaModel;
import net.xeill.elpuig.apipatitasconectadas.services.MascotaService;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    // Petición GET para obtener todas las mascotas
    @GetMapping
    public ArrayList<MascotaModel> getMascotas() {
        return this.mascotaService.getMascotas();
    }

    // Petición POST para guardar una nueva mascota
    @PostMapping
    public ResponseEntity<?> saveMascota(@RequestBody MascotaModel mascota) {
        try {
            MascotaModel savedMascota = this.mascotaService.saveMascota(mascota);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMascota);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al guardar la mascota: " + e.getMessage());
        }
    }

    // Petición GET para obtener una mascota por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getMascotaById(@PathVariable("id") Long id) {
        Optional<MascotaModel> mascota = this.mascotaService.getById(id);
        return mascota.isPresent()
            ? ResponseEntity.ok(mascota.get())
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mascota no encontrada con ID: " + id);
    }

    // Petición PUT para actualizar una mascota por su ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMascotaById(@RequestBody MascotaModel request, @PathVariable("id") Long id) {
        try {
            MascotaModel updatedMascota = this.mascotaService.updateByID(request, id);
            return ResponseEntity.ok(updatedMascota);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al actualizar la mascota: " + e.getMessage());
        }
    }

    // Petición DELETE para eliminar una mascota por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMascotaById(@PathVariable("id") Long id) {
        boolean ok = this.mascotaService.deleteMascota(id);
        if (ok) {
            return ResponseEntity.ok("Mascota eliminada con ID: " + id);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("No se pudo eliminar la mascota con ID: " + id);
        }
    }
}
