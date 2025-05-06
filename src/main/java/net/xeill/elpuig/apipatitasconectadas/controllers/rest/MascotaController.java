package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.MascotaModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.MascotaModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.MascotaModel;
import net.xeill.elpuig.apipatitasconectadas.services.MascotaService;

/**
 * Controlador REST para gestionar operaciones relacionadas con mascotas de usuarios.
 * Proporciona endpoints para crear, leer, actualizar y eliminar mascotas
 * asociadas a un usuario específico.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping("/usuarios/{usuarioId}/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    /**
     * Obtiene todas las mascotas asociadas a un usuario específico
     * @param usuarioId ID del usuario cuyas mascotas se quieren obtener
     * @return ResponseEntity con lista de mascotas en formato DTO o mensaje de error
     */
    @GetMapping
    public ResponseEntity<?> getMascotasByUsuario(@PathVariable("usuarioId") Long usuarioId) {
        try {
            List<MascotaModel> mascotas = mascotaService.getMascotasByUsuario(usuarioId);
            List<MascotaModelDtoResponse> mascotasDto = mascotas.stream()
                .map(MascotaModelDtoResponse::new)
                .collect(Collectors.toList());
            return new ResponseEntity<>(mascotasDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Crea una nueva mascota asociada a un usuario específico
     * @param usuarioId ID del usuario al que se asociará la mascota
     * @param mascotaDto Datos de la mascota en formato DTO
     * @return ResponseEntity con la mascota creada o mensaje de error
     */
    @PostMapping
    public ResponseEntity<?> saveMascota(
            @PathVariable("usuarioId") Long usuarioId, 
            @RequestBody MascotaModelDtoRequest mascotaDto) {
        try {
            // Convertir DTO a modelo
            MascotaModel mascota = mascotaDto.toDomain();
            mascota.setUsuarioId(usuarioId); // Aseguramos que se asigne el ID del usuario
            
            // Guardar mascota
            MascotaModel saved = mascotaService.saveMascota(mascota);
            
            // Convertir a DTO para la respuesta
            MascotaModelDtoResponse response = new MascotaModelDtoResponse(saved);
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error al guardar la mascota: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene una mascota específica de un usuario por su ID
     * @param usuarioId ID del usuario propietario de la mascota
     * @param mascotaId ID de la mascota a buscar
     * @return ResponseEntity con la mascota encontrada o mensaje de error
     */
    @GetMapping("/{mascotaId}")
    public ResponseEntity<?> getMascotaById(
            @PathVariable("usuarioId") Long usuarioId,
            @PathVariable("mascotaId") Long mascotaId) {
        try {
            Optional<MascotaModel> mascota = mascotaService.getByIdAndUsuarioId(mascotaId, usuarioId);
            
            if (mascota.isPresent()) {
                MascotaModelDtoResponse mascotaDto = new MascotaModelDtoResponse(mascota.get());
                return new ResponseEntity<>(mascotaDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "Mascota no encontrada con ID: " + mascotaId), 
                    HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza una mascota específica de un usuario
     * @param usuarioId ID del usuario propietario de la mascota
     * @param mascotaId ID de la mascota a actualizar
     * @param mascotaDto Datos actualizados de la mascota
     * @return ResponseEntity con la mascota actualizada o mensaje de error
     */
    @PutMapping("/{mascotaId}")
    public ResponseEntity<?> updateMascota(
            @PathVariable("usuarioId") Long usuarioId,
            @PathVariable("mascotaId") Long mascotaId,
            @RequestBody MascotaModelDtoRequest mascotaDto) {
        try {
            // Convertir DTO a modelo
            MascotaModel mascota = mascotaDto.toDomain();
            mascota.setUsuarioId(usuarioId);
            
            // Actualizar mascota
            MascotaModel updated = mascotaService.updateByIdAndUsuarioId(mascota, mascotaId, usuarioId);
            
            // Convertir a DTO para la respuesta
            MascotaModelDtoResponse response = new MascotaModelDtoResponse(updated);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error al actualizar la mascota: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina una mascota específica de un usuario
     * @param usuarioId ID del usuario propietario de la mascota
     * @param mascotaId ID de la mascota a eliminar
     * @return ResponseEntity con mensaje de confirmación o error
     */
    @DeleteMapping("/{mascotaId}")
    public ResponseEntity<?> deleteMascota(
            @PathVariable("usuarioId") Long usuarioId,
            @PathVariable("mascotaId") Long mascotaId) {
        try {
            boolean deleted = mascotaService.deleteMascotaByUsuario(mascotaId, usuarioId);
            
            if (deleted) {
                return new ResponseEntity<>(Map.of("mensaje", "Mascota eliminada con ID: " + mascotaId), 
                    HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "Mascota no encontrada o no pertenece al usuario"), 
                    HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
