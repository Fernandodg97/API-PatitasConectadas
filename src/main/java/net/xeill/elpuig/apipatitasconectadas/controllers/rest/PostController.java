package net.xeill.elpuig.apipatitasconectadas.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.xeill.elpuig.apipatitasconectadas.models.PostModel;
import net.xeill.elpuig.apipatitasconectadas.services.PostService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // Petición GET para obtener publicaciones
    // Permite filtrar por contenido o por rango de fechas opcionalmente
    @GetMapping
    public ResponseEntity<List<PostModel>> getPosts(
            @RequestParam(required = false) String contenido,
            @RequestParam(required = false) LocalDateTime fechaInicio,
            @RequestParam(required = false) LocalDateTime fechaFin) {

        // Si se proporciona contenido, se hace una búsqueda por contenido
        if (contenido != null) {
            return ResponseEntity.ok(postService.searchPostsByContent(contenido));
        }

        // Si se proporciona un rango de fechas, se filtran las publicaciones entre esas
        // fechas
        if (fechaInicio != null && fechaFin != null) {
            return ResponseEntity.ok(postService.getPostsByDateRange(fechaInicio, fechaFin));
        }

        // Si no hay filtros, se devuelven todas las publicaciones
        return ResponseEntity.ok(postService.getPosts());
    }

    // Petición POST para crear una nueva publicación
    @PostMapping
    public ResponseEntity<?> savePost(@RequestBody PostModel post) {
        try {
            // Guarda el post y devuelve 201 (CREATED)
            PostModel savedPost = postService.savePost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
        } catch (ValidationException e) {
            // Maneja errores de validación
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            // Maneja cualquier otro error interno
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error al crear el post: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Petición GET para obtener una publicación por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        try {
            PostModel post = postService.getById(id);
            return ResponseEntity.ok(post);
        } catch (EntityNotFoundException e) {
            // Devuelve 404 si no se encuentra la publicación
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Petición PUT para actualizar una publicación por su ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@RequestBody PostModel request, @PathVariable Long id) {
        try {
            PostModel updatedPost = postService.updateById(request, id);
            return ResponseEntity.ok(updatedPost);
        } catch (EntityNotFoundException e) {
            // Si no se encuentra el post
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (ValidationException e) {
            // Si hay un error de validación
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            // Otros errores generales
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error al actualizar el post: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Petición DELETE para eliminar una publicación por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            boolean deleted = postService.deletePost(id);
            if (deleted) {
                // Si se elimina correctamente
                Map<String, String> response = new HashMap<>();
                response.put("message", "Post eliminado correctamente");
                return ResponseEntity.ok(response);
            } else {
                // Si no se puede eliminar (por ejemplo, no existe)
                Map<String, String> response = new HashMap<>();
                response.put("error", "No se pudo eliminar el post");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            // Error inesperado al eliminar
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error al eliminar el post: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Petición GET para obtener todas las publicaciones de un usuario específico
    @GetMapping("/usuarios/{userId}/posts")
    public ResponseEntity<?> getPostsByUser(@PathVariable Long userId) {
        try {
            List<PostModel> posts = postService.getPostsByUser(userId);
            return ResponseEntity.ok(posts);
        } catch (EntityNotFoundException e) {
            // Si no se encuentra el usuario
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Petición GET para obtener todas las publicaciones de un grupo específico
    @GetMapping("/grupos/{grupoId}/posts")
    public ResponseEntity<?> getPostsByGrupo(@PathVariable Long grupoId) {
        try {
            List<PostModel> posts = postService.getPostsByGrupo(grupoId);
            return ResponseEntity.ok(posts);
        } catch (EntityNotFoundException e) {
            // Si no se encuentra el grupo
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
