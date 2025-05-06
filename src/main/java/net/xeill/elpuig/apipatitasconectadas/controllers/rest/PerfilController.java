package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.PerfilModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.PerfilModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.PerfilModel;
import net.xeill.elpuig.apipatitasconectadas.services.PerfilService;

/**
 * Controlador REST para gestionar operaciones relacionadas con perfiles de usuarios.
 * Proporciona endpoints para crear, leer, actualizar y eliminar perfiles.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    /**
     * Obtiene todos los perfiles existentes en el sistema
     * @return ResponseEntity con lista de perfiles en formato DTO o mensaje de error
     */
    @GetMapping(path = "/perfiles")
    public ResponseEntity<?> getPerfiles() {
        try {
            // Llama al servicio para obtener la lista de perfiles y la retorna
            List<PerfilModelDtoResponse> perfiles = perfilService.getPerfiles().stream()
                .map(PerfilModelDtoResponse::new)
                .collect(Collectors.toList());
            return ResponseEntity.ok(perfiles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener los perfiles: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo perfil en el sistema
     * @param perfilDto Datos del perfil en formato DTO
     * @return ResponseEntity con el perfil creado o mensaje de error
     */
    @PostMapping(path = "/perfiles")
    public ResponseEntity<?> savePerfil(@RequestBody PerfilModelDtoRequest perfilDto) {
        try {
            // Convierte el DTO a modelo
            PerfilModel perfil = perfilDto.toDomain();
            
            // Intenta guardar el perfil recibido en el cuerpo de la petición
            PerfilModel savedPerfil = this.perfilService.savePerfil(perfil);
            
            // Devuelve el perfil guardado con estado HTTP 201 (CREATED)
            return ResponseEntity.status(HttpStatus.CREATED).body(new PerfilModelDtoResponse(savedPerfil));
        } catch (Exception e) {
            // En caso de error, devuelve estado HTTP 500 (INTERNAL SERVER ERROR) con el mensaje del error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el perfil: " + e.getMessage());
        }
    }

    /**
     * Obtiene el perfil asociado a un usuario específico
     * @param id ID del usuario cuyo perfil se busca
     * @return ResponseEntity con el perfil encontrado o mensaje de error
     */
    @GetMapping(path = "/usuarios/{id}/perfiles")
    public ResponseEntity<?> getPerfilById(@PathVariable("id") Long id) {
        try {
            // Llama al servicio para buscar el perfil por ID
            var perfilOptional = this.perfilService.getById(id);
            
            if (perfilOptional.isPresent()) {
                return ResponseEntity.ok(new PerfilModelDtoResponse(perfilOptional.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró el perfil con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener el perfil: " + e.getMessage());
        }
    }

    /**
     * Actualiza el perfil de un usuario específico
     * @param perfilDto Datos actualizados del perfil
     * @param id ID del usuario cuyo perfil se actualizará
     * @return ResponseEntity con el perfil actualizado o mensaje de error
     */
    @PutMapping(path = "/usuarios/{id}/perfiles")
    public ResponseEntity<?> updatePerfilById(@RequestBody PerfilModelDtoRequest perfilDto, @PathVariable("id") Long id) {
        try {
            // Convierte el DTO a modelo
            PerfilModel perfil = perfilDto.toDomain();
            
            // Llama al servicio para actualizar el perfil con los nuevos datos
            PerfilModel updatedPerfil = this.perfilService.updateByID(perfil, id);
            
            // Retorna el perfil actualizado
            return ResponseEntity.ok(new PerfilModelDtoResponse(updatedPerfil));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al actualizar el perfil: " + e.getMessage());
        }
    }

    /**
     * Elimina el perfil de un usuario específico
     * @param id ID del usuario cuyo perfil se eliminará
     * @return ResponseEntity con mensaje de confirmación o error
     */
    @DeleteMapping(path = "/usuarios/{id}/perfiles")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try {
            // Intenta eliminar el perfil con el ID especificado
            boolean ok = this.perfilService.deletePerfil(id);
    
            // Devuelve un mensaje indicando si la eliminación fue exitosa o no
            if (ok) {
                return ResponseEntity.ok("Se ha eliminado el perfil con id: " + id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se ha podido eliminar el perfil con id: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al eliminar el perfil: " + e.getMessage());
        }
    }
}
