package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.UsuarioComentarioModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.UsuarioComentarioModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.services.UsuarioComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las relaciones entre usuarios y comentarios.
 * Proporciona endpoints para crear, consultar y eliminar las relaciones
 * entre usuarios y comentarios en el sistema.
 * Todas las respuestas son encapsuladas en objetos ResponseEntity para un manejo
 * consistente de la comunicación HTTP.
 */
@RestController
@RequestMapping("/usuario-comentario")
public class UsuarioComentarioController {

    @Autowired
    private UsuarioComentarioService usuarioComentarioService;

    /**
     * Obtiene todas las relaciones usuario-comentario existentes
     * @return ResponseEntity con lista de relaciones en formato DTO
     */
    @GetMapping
    public ResponseEntity<List<UsuarioComentarioModelDtoResponse>> getAllUsuarioComentarios() {
        return ResponseEntity.ok(usuarioComentarioService.getAllUsuarioComentarios());
    }

    /**
     * Obtiene una relación usuario-comentario específica por su ID
     * @param id ID de la relación a buscar
     * @return ResponseEntity con la relación encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioComentarioModelDtoResponse> getUsuarioComentarioById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioComentarioService.getUsuarioComentarioById(id));
    }

    /**
     * Obtiene todas las relaciones usuario-comentario de un usuario específico
     * @param usuarioId ID del usuario cuyas relaciones se quieren obtener
     * @return ResponseEntity con lista de relaciones del usuario
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<UsuarioComentarioModelDtoResponse>> getUsuarioComentariosByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioComentarioService.getUsuarioComentariosByUsuario(usuarioId));
    }

    /**
     * Obtiene todas las relaciones usuario-comentario de un comentario específico
     * @param comentarioId ID del comentario cuyas relaciones se quieren obtener
     * @return ResponseEntity con lista de relaciones del comentario
     */
    @GetMapping("/comentario/{comentarioId}")
    public ResponseEntity<List<UsuarioComentarioModelDtoResponse>> getUsuarioComentariosByComentario(@PathVariable Long comentarioId) {
        return ResponseEntity.ok(usuarioComentarioService.getUsuarioComentariosByComentario(comentarioId));
    }

    /**
     * Crea una nueva relación usuario-comentario
     * @param request Datos de la relación en formato DTO
     * @return ResponseEntity con la relación creada
     */
    @PostMapping
    public ResponseEntity<UsuarioComentarioModelDtoResponse> createUsuarioComentario(@RequestBody UsuarioComentarioModelDtoRequest request) {
        return ResponseEntity.ok(usuarioComentarioService.createUsuarioComentario(request));
    }

    /**
     * Elimina una relación usuario-comentario específica
     * @param id ID de la relación a eliminar
     * @return ResponseEntity sin contenido (204 No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuarioComentario(@PathVariable Long id) {
        usuarioComentarioService.deleteUsuarioComentario(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Elimina todas las relaciones usuario-comentario de un usuario específico
     * @param usuarioId ID del usuario cuyas relaciones se quieren eliminar
     * @return ResponseEntity sin contenido (204 No Content)
     */
    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<Void> deleteUsuarioComentariosByUsuario(@PathVariable Long usuarioId) {
        usuarioComentarioService.deleteUsuarioComentariosByUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Elimina todas las relaciones usuario-comentario de un comentario específico
     * @param comentarioId ID del comentario cuyas relaciones se quieren eliminar
     * @return ResponseEntity sin contenido (204 No Content)
     */
    @DeleteMapping("/comentario/{comentarioId}")
    public ResponseEntity<Void> deleteUsuarioComentariosByComentario(@PathVariable Long comentarioId) {
        usuarioComentarioService.deleteUsuarioComentariosByComentario(comentarioId);
        return ResponseEntity.noContent().build();
    }
} 